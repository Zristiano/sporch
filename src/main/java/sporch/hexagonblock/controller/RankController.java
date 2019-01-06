package sporch.hexagonblock.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import sporch.hexagonblock.data.RestfulResult;
import sporch.hexagonblock.data.UserPlatform;
import sporch.hexagonblock.data.WorldRank;
import sporch.hexagonblock.model.IndividualScoreRecord;
import sporch.hexagonblock.model.ScoreRecord;
import sporch.hexagonblock.repositories.IndividualScoreRepo;
import sporch.hexagonblock.repositories.ScoreRankRepo;
import sporch.hexagonblock.utils.MethodUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class RankController {

    @RequestMapping(value = "/",method = RequestMethod.GET,params = {"id","name"})
    public String test(@RequestParam String name, @RequestParam String id){
        System.out.println("id->"+id);
        return "hello world" + "id->"+id+"   name->"+name +" timeStamp->"+System.currentTimeMillis()/1000;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ScoreRankRepo scoreRankRepo;

    @Autowired
    private IndividualScoreRepo individualScoreRepo;

    private JsonParser jsonParser;

    {
        jsonParser = new JsonParser();
    }

    @RequestMapping(value = "/jdbc/score", method = RequestMethod.GET)
    public List<Map<String,Object>> getJDBCData(@RequestParam(value = "username") String username,
                                                @RequestParam(value = "platform", required = false) Integer platform,
                                                @RequestParam(value = "score") Integer score){
        List<Map<String,Object>> result = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO AllScores (username, platform, score, ts)" +
                    " VALUES (?,?,?,?);");
            pstmt.setString(1,username);
            if (platform==null){
                platform = 0;
            }
            pstmt.setInt(2,platform);
            pstmt.setInt(3,score);
            pstmt.setInt(4,(int)(System.currentTimeMillis()/1000));
            pstmt.executeUpdate();

            result = jdbcTemplate.queryForList("SELECT * FROM AllScores");
        }catch (SQLException e){
            System.out.println(""+e);
            return null ;
        }
        return result;
    }

    /**
     * get the score ranking list
     * @param username username
     * @param platform platform
     * @return if username is given, it will return world ranking list，or it will return individual ranking list
     */
    @RequestMapping(value = {"/rank"},method = RequestMethod.GET)
    public RestfulResult<WorldRank> getRankList(@RequestParam(value="username",required=false)String username,
                                                @RequestParam(value="platform",required=false)Integer platform){
        if (MethodUtil.isEmpty(username)){
            WorldRank<ScoreRecord> worldRank = new WorldRank<>();
            // if there is no username,
            // return the the top 20 highest score records from the world score records
            worldRank.setRankList(getRankList(0,50,null,scoreRankRepo));
            return new RestfulResult<>(0,"success",worldRank);
        }else {
            WorldRank<IndividualScoreRecord> worldRank = new WorldRank<>();
            // return all the records of given user
            if (platform == null) platform = 0;
            IndividualScoreRecord scoreRecord = new IndividualScoreRecord();
            scoreRecord.setUsername(username);
            scoreRecord.setPlatform(platform);
            // narrow down the searching scope
            ExampleMatcher.GenericPropertyMatcher propertyMatcher =  ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.EXACT);
            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("username", propertyMatcher)
                    .withMatcher("platform",propertyMatcher);
            Example<IndividualScoreRecord> example = Example.of(scoreRecord,exampleMatcher);
            worldRank.setRankList(getRankList(0,IndividualScoreRecord.MAX_INDIVIDUAL_COUNT,example,individualScoreRepo));
            return new RestfulResult<>(0,"success",worldRank);
        }
    }

    @RequestMapping(path = "/score", method = RequestMethod.POST )
    public RestfulResult<Object> updateScore(@RequestBody String body) {
        try {
            JsonElement jsonElement = jsonParser.parse(body);
            JsonObject json = jsonElement.getAsJsonObject();
            String username = json.get("username").getAsString();
            Integer score = json.get("score").getAsInt();
            ScoreRecord scoreRecord = new ScoreRecord();
            scoreRecord.setUsername(username);
            scoreRecord.setScore(score);
            if (json.get("platform") != null) {
                int platform = json.get("platform").getAsInt();
                if (platform > 0 && platform <= UserPlatform.getCount()) {
                    scoreRecord.setPlatform(platform);
                }
            }
            if (json.get("avatar") !=null){
                scoreRecord.setAvatar(json.get("avatar").getAsString());
            }
            scoreRecord.setTs((int) (System.currentTimeMillis() / 1000));
            updateIndividualRecord(scoreRecord);
            ScoreRecord existRecord = scoreRankRepo.findByUsernameAndPlatform(scoreRecord.getUsername(), scoreRecord.getPlatform());
            // check if the user exists in the world rank table
            if (existRecord != null) {
                // if current score is better than before, update it.
                if (existRecord.getScore() <= scoreRecord.getScore()) {
                    scoreRankRepo.updateScore(existRecord.getId(), scoreRecord.getScore(), scoreRecord.getTs());
                }
            } else {
                // insert a new score record
                scoreRecord = scoreRankRepo.save(scoreRecord);
            }
            // rank of current score in the world score records
            int rank = scoreRankRepo.rankInTheWorld(score);
            WorldRank<ScoreRecord> worldRank = new WorldRank<>();
            // get the the top 20 highest score records
            worldRank.setRankList(getRankList(0, 50, null, scoreRankRepo));
            worldRank.setIndividualRank(rank);

            updateIndividualRecord(scoreRecord);
            return new RestfulResult<>(0, "success", worldRank);
        } catch (JsonParseException | NullPointerException e) {
            return new RestfulResult<>(1, "field score and field username are required", null);
        } catch (IllegalStateException e) {
            return new RestfulResult<>(1, e.getMessage(), null);
        }
    }

    /**
     * update the individual score table
     * @param s score record
     */
    private void updateIndividualRecord(ScoreRecord s){
        IndividualScoreRecord record = new IndividualScoreRecord(s);
        List<IndividualScoreRecord> records = individualScoreRepo.findAllByUsernameAndPlatformOrderByScore(record.getUsername(),record.getPlatform());
        // if number of individual records is less than MAX_INDIVIDUAL_COUNT, insert a new record,
        if (records.size()<IndividualScoreRecord.MAX_INDIVIDUAL_COUNT){
            individualScoreRepo.save(record);
        }else {
            //update the minimum score records
            individualScoreRepo.updateScore(record.getScore(),records.get(records.size()-1).getId(),record.getTs());
        }
    }

    /**
     * get certain part of list of world score records in descending order of score
     * @param page requested page of the rank list
     * @param size size of every page
     * @param example used to narrow down the searching scope
     * @return list of world score records in descending order
     */
    private <T extends ScoreRecord> List<T> getRankList(int page, int size, Example<T> example, JpaRepository<T,Integer> repo){
        Sort sort = Sort.by(Sort.Order.desc(T.COL_SCORE));
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<T> pageItem;
        if (example==null){
            pageItem = repo.findAll(pageable);
        }else {
            pageItem = repo.findAll(example,pageable);
        }
        return pageItem.getContent();
    }


}

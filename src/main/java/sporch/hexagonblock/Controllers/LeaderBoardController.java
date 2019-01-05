package sporch.hexagonblock.Controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sporch.hexagonblock.Constants;
import sporch.hexagonblock.Models.GlobalLeaderBoard;
import sporch.hexagonblock.Models.LeaderBoard;
import sporch.hexagonblock.Repositories.GlobalLeaderBoardRepository;
import sporch.hexagonblock.Repositories.LeaderBoardRepository;
import sporch.hexagonblock.Services.LeaderBoardService;

import java.util.List;

@RestController
public class LeaderBoardController {
    @Autowired
    private LeaderBoardRepository leaderBoardRepository;

    @Autowired
    private  GlobalLeaderBoardRepository globalLeaderBoardRepository;

    @RequestMapping(value = "/rank", method = RequestMethod.GET,params = {"page"})
    public @ResponseBody Page<GlobalLeaderBoard> getGlobalLeaderBoard(@RequestParam Integer page){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"score");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(page, Constants.PAGESIZE,sort);
        return globalLeaderBoardRepository.findAll(pageRequest);

    }

    @RequestMapping(value = "/rank",method = RequestMethod.GET,params = {"userId"})
    public  @ResponseBody List<LeaderBoard> getLeaderBoard(@RequestParam String userId){
            System.out.println(leaderBoardRepository.findAllByUsernameOrderByScore(userId));
        return leaderBoardRepository.findAllByUsernameOrderByScore(userId);
    }

    @RequestMapping(value = "/rank",method = RequestMethod.POST)
    public  @ResponseBody Page<GlobalLeaderBoard> updateScoreInfo(@RequestBody String leaderBoardInfo){
        Gson gson = new Gson();
        LeaderBoard newleaderBoard = gson.fromJson(leaderBoardInfo,LeaderBoard.class);
        GlobalLeaderBoard newgloballeaderBoard = gson.fromJson(leaderBoardInfo,GlobalLeaderBoard.class);
        leaderBoardRepository.save(newleaderBoard);
//        globalLeaderBoardRepository.save(newgloballeaderBoard);
        String searchKey = newgloballeaderBoard.getUsername();
        GlobalLeaderBoard foundGlobalLB = globalLeaderBoardRepository.findDistinctByUsername(searchKey);
        if(foundGlobalLB!=null){
            if(foundGlobalLB.getScore()>=newgloballeaderBoard.getScore()){}
            else{
                globalLeaderBoardRepository.deleteByUsername(searchKey);
                globalLeaderBoardRepository.save(newgloballeaderBoard);

            }
        }else{
            globalLeaderBoardRepository.save(newgloballeaderBoard);
        }
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"score");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(Constants.DEFAULTPAGE, Constants.PAGESIZE,sort);
        return globalLeaderBoardRepository.findAll(pageRequest);
    }
//    @RequestMapping(value = "/ranktest",method = RequestMethod.POST)
//    public  @ResponseBody String testUpdate(@RequestBody String leaderBoardInfo){
//        Gson gson = new Gson();
//        LeaderBoard newleaderBoard = gson.fromJson(leaderBoardInfo,LeaderBoard.class);
//        GlobalLeaderBoard newgloballeaderBoard = gson.fromJson(leaderBoardInfo,GlobalLeaderBoard.class);
//        String searchKey = newgloballeaderBoard.getUsername();
//        GlobalLeaderBoard foundGlobalLB = globalLeaderBoardRepository.findDistinctByUsername(searchKey);
//        if(foundGlobalLB!=null){
//            if(foundGlobalLB.getScore()>=newgloballeaderBoard.getScore()){}
//            else{
//                globalLeaderBoardRepository.deleteByUsername(searchKey);
//                globalLeaderBoardRepository.save(newgloballeaderBoard);
//
//            }
//        }else{
//            globalLeaderBoardRepository.save(newgloballeaderBoard);
//        }
//        leaderBoardRepository.save(newleaderBoard);
//        return "SUCCESS";
//    }

    @RequestMapping(value = "/gall",method = RequestMethod.GET)
    public @ResponseBody Iterable<GlobalLeaderBoard> getAllGTest(){
        return globalLeaderBoardRepository.findAll();
    }
    @RequestMapping(value = "/uall",method = RequestMethod.GET)
    public @ResponseBody Iterable<LeaderBoard> getAllUTest(){
        return leaderBoardRepository.findAll();
    }
}

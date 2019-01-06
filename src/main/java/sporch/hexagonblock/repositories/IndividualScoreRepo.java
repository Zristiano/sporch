package sporch.hexagonblock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sporch.hexagonblock.model.IndividualScoreRecord;

import javax.transaction.Transactional;
import java.util.List;


public interface IndividualScoreRepo extends JpaRepository<IndividualScoreRecord,Integer> {
    List<IndividualScoreRecord> findAllByUsernameAndPlatformOrderByScore(String username, Integer platform);

    @Query("select r from IndividualScoreRecord r where r.username=:username and r.platform=:platform order by r.score")
    List<IndividualScoreRepo> individualResult(@Param("username") String username, @Param("platform") Integer platform);

    @Transactional
    @Modifying
    @Query("update IndividualScoreRecord r set r.score=:score, r.ts=:ts where r.id=:id")
    int updateScore(@Param("score") Integer score, @Param("id") Integer id, @Param("ts") Integer ts);
}

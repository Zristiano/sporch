package sporch.hexagonblock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sporch.hexagonblock.model.ScoreRecord;

public interface ScoreRankRepo extends JpaRepository<ScoreRecord,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE ScoreRecord w SET w.score = :score, w.ts = :ts WHERE id=:id")
    int updateScore(@Param("id") Integer id,
                    @Param("score") Integer score,
                    @Param("ts") Integer ts);

    ScoreRecord findByUsernameAndPlatform(String username, Integer platform);

    @Query("SELECT count (id) + 1 as rank from ScoreRecord where score>:score")
    int rankInTheWorld(@Param("score") Integer score);
}

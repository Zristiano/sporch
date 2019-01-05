package sporch.hexagonblock.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sporch.hexagonblock.Models.GlobalLeaderBoard;

import javax.transaction.Transactional;

public interface GlobalLeaderBoardRepository extends PagingAndSortingRepository<GlobalLeaderBoard,Integer> {

    @Query("SELECT glb FROM GlobalLeaderBoard glb WHERE glb.username = :username")
    GlobalLeaderBoard findDistinctByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM GlobalLeaderBoard  glb WHERE glb.username = :username")
    void deleteByUsername(@Param("username") String Username);
//    void deleteByUsername(String username);
}

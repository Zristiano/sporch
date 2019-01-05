package sporch.hexagonblock.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sporch.hexagonblock.Models.LeaderBoard;

import java.util.List;

//@RepositoryDefinition(domainClass = LeaderBoard.class, idClass = Integer.class)
public interface LeaderBoardRepository extends CrudRepository<LeaderBoard,Integer> {

    @Query("SELECT lb FROM LeaderBoard lb WHERE lb.username = :username ORDER BY lb.score DESC")
    List<LeaderBoard> findAllByUsernameOrderByScore(@Param("username") String username);
//    public Page<LeaderBoard> findAllByUserId(String userid, Pageable pageable);

}

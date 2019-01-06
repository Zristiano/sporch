package sporch.hexagonblock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sporch.hexagonblock.controller.RankController;
import sporch.hexagonblock.repositories.ScoreRankRepo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HexagonblockApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AllScores( " +
//                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
//                "username VARCHAR (20) NOT NULL, " +
//                "platform INTEGER DEFAULT 0, " +
//                "score INTEGER NOT NULL, " +
//                "ts INTEGER default 0);");
//        stmt.executeUpdate("DROP TABLE IF EXISTS AllScores;");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AllScores (\n" +
//                "  id INTEGER PRIMARY KEY AUTO_INCREMENT," +
//                "  username VARCHAR(20) NOT NULL ," +
//                "  platform INTEGER DEFAULT 0," +
//                "  score INTEGER NOT NULL," +
//                "  avatar VARCHAR (2083) DEFAULT 'https://res.cloudinary.com/hi3jyavvz/image/upload/v1543401064/fdaqdrekeouta8s2nldr.jpg'," +
//                "  ts INTEGER" +
//                ");");
        conn.close();
    }

    @Autowired
    RankController controller;

    @Test
    public void testJPAData(){
//        List<ScoreRecord> records = controller.getScoreWorldRank("Yuanmeng",15020,2);
//        System.out.println(records);
    }

    @Autowired
    ScoreRankRepo scoreRankRepo;
    @Test
    public void testRank(){
        int rank = scoreRankRepo.rankInTheWorld(56000);
        System.out.println(rank);
    }
}


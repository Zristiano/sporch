package sporch.hexagonblock.model;

import javax.persistence.*;

@Entity
@Table(name = "all_score")
public class IndividualScoreRecord extends ScoreRecord {

    public static final int MAX_INDIVIDUAL_COUNT = 3;

    public IndividualScoreRecord(){}

    public IndividualScoreRecord(ScoreRecord s){
        setUsername(s.getUsername());
        setTs(s.getTs());
        setScore(s.getScore());
        setPlatform(s.getPlatform());
        setAvatar(s.getAvatar());
    }


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Override
//    public Integer getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = id;
//    }

//    @Override
//    public String toString() {
//        return "IndividualScoreRecord{" +
//                "id=" + id +
//                ", username='" + getUsername() + '\'' +
//                ", platform=" + getPlatform() +
//                ", score=" + getScore() +
//                ", avatar=" + getAvatar() +
//                ", ts=" + getTs() +
//                '}';
//    }
}

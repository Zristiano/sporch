package sporch.hexagonblock.model;

import javax.persistence.*;

@Entity
@Table(name = "all_score")
public class IndividualScoreRecord {

    public static final String COL_SCORE = "score";

    public static final int MAX_INDIVIDUAL_COUNT = 3;

    public IndividualScoreRecord(){}

    public IndividualScoreRecord(WorldScoreRecord s){
        setUsername(s.getUsername());
        setTs(s.getTs());
        setScore(s.getScore());
        setPlatform(s.getPlatform());
        setAvatar(s.getAvatar());
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String username;

    /**
     * platform where user login from
     */
    private Integer platform;

    @Column(name = COL_SCORE, nullable = false)
    private Integer score;

    @Column (length = 2083)
    private String avatar;

    private Integer ts;

    {
        platform = 0;
        avatar = "https://res.cloudinary.com/hi3jyavvz/image/upload/v1543401064/fdaqdrekeouta8s2nldr.jpg";
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IndividualScoreRecord{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", platform=" + platform +
                ", score=" + score +
                ", avatar='" + avatar + '\'' +
                ", ts=" + ts +
                '}';
    }
}

package sporch.hexagonblock.model;

import javax.persistence.*;

@Entity // inform JPA this class reflects table in database
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ScoreRecord {

    public static final String COL_SCORE = "score";

    @Id  // primary key
    @GeneratedValue(strategy = GenerationType.TABLE)  // auto_increment
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ScoreRecord{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", platform=" + platform +
                ", score=" + score +
                ", avatar=" + avatar +
                ", ts=" + ts +
                '}';
    }
}

package sporch.hexagonblock.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GlobalLeaderBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private Short platform;
    //1 - phone
    //2 - QQ
    //3 - wechat
    //4 - weibo
    //5 - facebook
    //6 - google
    //7 - twitter
    //8 - instagram
    private Integer score;

    public  GlobalLeaderBoard(){
    }

    public GlobalLeaderBoard(String username, Short platform, Integer score) {
        this.username = username;
        this.platform = platform;
        this.score = score;
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

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

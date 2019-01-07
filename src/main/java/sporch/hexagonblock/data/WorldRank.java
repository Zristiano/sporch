package sporch.hexagonblock.data;

import java.util.List;

public class WorldRank<T> {
    List<T> rankList;
    int individualRank = -1;

    public List<T> getRankList() {
        return rankList;
    }

    public void setRankList(List<T> rankList) {
        this.rankList = rankList;
    }

    public int getIndividualRank() {
        return individualRank;
    }

    public void setIndividualRank(int individualRank) {
        this.individualRank = individualRank;
    }
}

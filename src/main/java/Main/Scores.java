package Main;

import java.util.ArrayList;
import java.util.List;

public class Scores {

    private List<Integer> scores = new ArrayList<Integer>();

    public void addScore(Integer score) {
        scores.add(score);
    }

    public List<Integer> printScores() {
        return scores;
    }

}

package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Scores {

    Player player = new Player();

    private static List<PlayerScore> scores = new ArrayList<PlayerScore>();

    String[] names = {"Ricardo", "Adriana", "Igor", "Pedro", "Andre", "Carl", "John", "Jane"};

    public void addScore(Integer score) {
        scores.add(new PlayerScore(player.getPlayerName(), score));
    }

    public List<PlayerScore> printScores() {
        return scores;
    }

}

class PlayerScore {

    private String name;
    private Integer score;

    public PlayerScore(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return this.name + " -> " +this.score;
    }

}
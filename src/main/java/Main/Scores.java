package Main;

import Database.Database;
import Player.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Scores {

    PlayerVars playerVars = new PlayerVars();
    Database connect = new Database();
    GameVars gameVars = new GameVars();

    private static List<PlayerScore> scores = new ArrayList<PlayerScore>();

//    String[] names = {"Ricardo", "Adriana", "Igor", "Pedro", "Andre", "Carl", "John", "Jane"};

    public void addScore(Integer score) {
        scores.add(new PlayerScore(playerVars.getPlayerName(), score));

        try {
            PreparedStatement addScore = connect.getConnection().prepareStatement("insert into scores (name, score, difficulty) values(?, ?, ?)");
            addScore.setString(1, playerVars.getPlayerName());
            addScore.setInt(2, score);
            addScore.setString(3, gameVars.getDifficulty());
            addScore.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

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
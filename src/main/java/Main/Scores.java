package Main;

import Database.Database;
import Player.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Scores {

    PlayerVars playerVars = new PlayerVars();
    Database connect = new Database();
    GameVars gameVars = new GameVars();

    private static List<PlayerScore> scoresList = new ArrayList<PlayerScore>();

//    String[] names = {"Ricardo", "Adriana", "Igor", "Pedro", "Andre", "Carl", "John", "Jane"};

    public void addScore(Integer score) {
//        scores.add(new PlayerScore(playerVars.getPlayerName(), score));

        try {
            PreparedStatement addScore = connect.getConnection().prepareStatement("insert into scores (name, score, difficulty) values(?, ?, ?)");
            addScore.setString(1, playerVars.getPlayerName());
            addScore.setInt(2, score);
            addScore.setString(3, gameVars.getDifficulty());
            addScore.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getScores() {
        try {
            PreparedStatement getScores = connect.getConnection().prepareStatement("select * from scores order by score desc limit 5");
            getScores.execute();
            ResultSet scores = getScores.getResultSet();
            while (scores.next()) {
                scoresList.add(new PlayerScore(scores.getString("name"), scores.getInt("score"), scores.getString("difficulty")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerScore> printScores() {
        return scoresList;
    }

}

class PlayerScore {

    private String name;
    private Integer score;
    private String difficulty;

    public PlayerScore(String name, Integer score, String difficulty) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }
    public Integer getScore() {
        return score;
    }
    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return this.name + " -> " +this.score;
    }

}
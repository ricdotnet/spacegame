package Player;

public class PlayerVars {

    public static String playerName;
    public static Integer playerScore = 0;

    public void setPlayerName(String name) {
        this.playerName = name;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerScore() {
        this.playerScore++;
    }
    public Integer getPlayerScore() {
        return playerScore;
    }
    public void resetPlayerScore() {
        playerScore = 0;
    }

}

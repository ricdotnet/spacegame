package Main;

public class GameVars {

    private static String difficulty = "Easy";
    private static Boolean isTesting = false;

    public void setDifficulty(String difficulty) {
        GameVars.difficulty = difficulty;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void setIsTesting(Boolean isTesting) {
        GameVars.isTesting = isTesting;
    }
    public Boolean getIsTesting() {
        return isTesting;
    }

}

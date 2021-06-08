package Main;

import java.util.Arrays;

public class GameVars {

    private static String difficulty = "Easy";
    private static Boolean isTesting = false;
    private static final Object[][] monsterType = {
            { "Weak", 1 },
            { "Tough", 3 },
            { "Colossal", 6 }
    };

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

    public String monsterObject() {
        return Arrays.deepToString(monsterType[(int) Math.ceil(Math.random() * 3) - 1]);
    }

}

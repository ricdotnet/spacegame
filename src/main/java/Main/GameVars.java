package Main;

import Util.Colors;

import java.awt.*;
import java.util.Arrays;

public class GameVars {

    private static String difficulty = "Easy";
    private static Boolean isTesting = false;
    private static final Object[][] monsterType = {
            { "Weak", 1 },
            { "Tough", 3 },
            { "Colossal", 6 }
    };

    private static final String[] starTypes = { "Close", "Medium", "Far" };
    private static final Color[] starColors = {
            Colors.BLUE_STAR,
            Colors.LIGHT_BLUE_STAR,
            Colors.CREAM_STAR,
            Colors.LIGHT_YELLOW_STAR,
            Colors.BRIGHT_YELLOW_STAR,
            Colors.ORANGE_STAR,
            Colors.RED_STAR
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

    public String starType() {
        return starTypes[(int) Math.ceil(Math.random() * 3) - 1];
    }

    public Color setStarColor() {
        return starColors[(int) Math.ceil(Math.random() * 7) - 1];
    }

}

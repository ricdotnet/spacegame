package Util;

import Main.MainClass;

public class Util {

    /**
     * Random X and random Y inside the window screen
     */
    public double setRandomX() {
        double x = Math.floor(Math.random() * (MainClass.WIDTH * MainClass.SCALE));

        if(x >= MainClass.WIDTH - 32) {
            x = x - 32;
        }
        return x;
    }
    public double setRandomY() {
        return Math.floor(Math.random() * (MainClass.HEIGHT * MainClass.SCALE));
    }

    /**
     * Random number between 0 - 1
     */
    public double chance() {
        return Math.random();
    }

}

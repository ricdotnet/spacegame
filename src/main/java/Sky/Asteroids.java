package Sky;

import Images.SprideSheet;
import Main.MainClass;
import Util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Asteroids {

    Util util = new Util();
    MainClass main;

    private static List<Asteroid> asteroidList = new LinkedList<>();

    public Asteroids(MainClass main) {
        this.main = main;
    }

    public void tick() {

//        if(util.chance() < 0.15) {
//            System.out.println("new asteroid");
//            asteroidList.add(new Asteroid(50, -32, 1, main));
//        }

        for(int i = 0; i < asteroidList.size(); i++) {
            asteroidList.get(i).setyPos(asteroidList.get(i).getSpeed());
            asteroidList.get(i).setRotation();
            if(asteroidList.get(i).getyPos() > main.getHeight()) {
                removeAsteroid(asteroidList.get(i));
            }
        }
    }

    /**
     * Remove asteroid
     * @param asteroid
     */
    public void removeAsteroid(Asteroid asteroid) {
        asteroidList.remove(asteroid);
    }

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

}
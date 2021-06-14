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

    private List<Asteroid> asteroidList = new LinkedList<>();

    public Asteroids(MainClass main) {
        this.main = main;
    }

    public void tick() {

//        if(util.chance() < 0.15) {
//            System.out.println("new asteroid");
//            asteroidList.add(new Asteroid(50, -32, 1, main));
//        }

        for(int i = 0; i < asteroidList.size(); i++) {
            asteroidList.get(i).setyPos(5);
            asteroidList.get(i).setRotation(5);
            if(asteroidList.get(i).getyPos() > main.getHeight()) {
                asteroidList.remove(i);
            }
        }
    }

    public void render(Graphics g) {

        for(int i = 0; i < asteroidList.size(); i++) {
//            asteroidList.get(i).tick();
            asteroidList.get(i).render(g);
        }
    }

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

}
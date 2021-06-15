package Sky;

import Images.SprideSheet;
import Main.MainClass;
import Util.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asteroid {

    MainClass main;
    Time time;

    private double xPos;
    private double yPos;
    private int speed;
    private final int rotationSpeed; // TODO: 14/06/2021 Make variable rotation speed.
    private int rotation;

    private final BufferedImage asteroid;

    public Asteroid(double xPos, double yPos, int speed, int rotationSpeed, MainClass main) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.main = main;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());
        asteroid = sprite.grabImage(5, 1, 32, 32);
    }

    public void render(Graphics2D g) {
        g.rotate(Math.toRadians(getRotation()), getxPos()+16, getyPos()+16);
        g.drawImage(asteroid, (int) getxPos(), (int) getyPos(), null);
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }
    public void setyPos(double yPos) {
        this.yPos = getyPos() + yPos;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setRotation() {
        this.rotation = getRotation() + rotationSpeed;
    }

    public double getxPos() {
        return xPos;
    }
    public double getyPos() {
        return yPos;
    }
    public int getSpeed() {
        return speed;
    }
    public int getRotation() {
        return rotation;
    }
}
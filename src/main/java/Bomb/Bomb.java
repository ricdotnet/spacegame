package Bomb;

import Main.MainClass;
import Images.SprideSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb {

    private double xPOS;
    private double yPOS;

    private BufferedImage bomb;

    public Bomb(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());

        bomb = sprite.grabImage(4, 1, 32, 32);
    }

    public void tick() {
        yPOS = yPOS + 5;
    }

    public void render(Graphics graphic) {
        graphic.drawImage(bomb, (int) xPOS, (int) yPOS, null);
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }

}

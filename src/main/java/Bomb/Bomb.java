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

        SprideSheet sprite = new SprideSheet(main.getIconsSheet());
        bomb = sprite.grabIcon(3, 1, 16, 16);
    }

    public void tick() {
        yPOS = yPOS + 5;
    }

    public void render(Graphics graphic) {
        graphic.drawImage(bomb, (int) xPOS + (16/2), (int) yPOS - 3, null);
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }

}

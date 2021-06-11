package Bullet;

import Main.MainClass;
import Images.SprideSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {

    private double xPOS;
    private double yPOS;

    private BufferedImage bullet;

    public Bullet(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getIconsSheet());
        bullet = sprite.grabIcon(2, 1, 16, 16);
    }

    public void tick() {
        yPOS = yPOS - 5;
    }

    public void render(Graphics graphic) {
        graphic.drawImage(bullet, (int) xPOS + (16/2), (int) yPOS + 15, null);
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }

}

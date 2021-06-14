package Bullet;

import Main.MainClass;
import Images.SprideSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class Bullet {

    private double xPOS;
    private double yPOS;
    private double rotation;

    private BufferedImage bullet;

    public Bullet(double xPOS, double yPOS, double rotation, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;
        this.rotation = rotation;

        SprideSheet sprite = new SprideSheet(main.getIconsSheet());
        bullet = sprite.grabIcon(2, 1, 16, 16);
    }

    public void tick() {
//        yPOS = yPOS - 5;
        this.xPOS += Math.cos(Math.toRadians(getRotation()-90)) * 5;
        this.yPOS += Math.sin(Math.toRadians(getRotation()-90)) * 5;
    }

    public void render(Graphics2D graphic) {
        graphic.rotate(Math.toRadians(getRotation()), (int) xPOS+8, (int) yPOS+8);
        graphic.drawImage(bullet, (int) xPOS + (16/2), (int) yPOS + 15, null);
//        graphic.dispose();
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }
//    public double getRotation() {
//        return rotation;
//    }
    public double getRotation() {
        DecimalFormat newRotation = new DecimalFormat("0.00");
        return Double.parseDouble(newRotation.format(rotation));
    }

}

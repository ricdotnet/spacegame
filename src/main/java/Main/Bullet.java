package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet {

    private double xPOS;
    private double yPOS;

    private BufferedImage bullet;

    public Bullet(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());

        bullet = sprite.grabImage(2, 1, 32, 32);
    }

    public void tick() {
        yPOS = yPOS - 5;
    }

    public void render(Graphics graphic) {
        graphic.drawImage(bullet, (int) xPOS, (int) yPOS, null);
    }

    public double getyPOS() {
        return yPOS;
    }

}

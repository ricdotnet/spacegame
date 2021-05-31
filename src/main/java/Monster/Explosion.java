package Monster;

import Images.SprideSheet;
import Main.MainClass;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion {

    private double xPOS;
    private double yPOS;
    private int explosionStage;

    private BufferedImage explosion;

    public Explosion(double xPOS, double yPOS, MainClass main, int explosionStage) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;
        this.explosionStage = explosionStage;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());
        explosion = sprite.grabImage(explosionStage, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics graphic) {
        graphic.drawImage(explosion, (int) xPOS, (int) yPOS, null);
    }

}

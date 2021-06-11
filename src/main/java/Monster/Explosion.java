package Monster;

import Images.SprideSheet;
import Main.ImageLoader;
import Main.MainClass;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion {

    ImageLoader imageLoader = new ImageLoader();

    private double xPOS;
    private double yPOS;
    private int explosionStage;

    private BufferedImage explosion;

    public Explosion(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());
//        explosion = imageLoader.loadImage("/poop.png");
        explosion = sprite.grabImage(4, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics graphic) {
        graphic.drawImage(explosion, (int) xPOS, (int) yPOS, null);
    }

}

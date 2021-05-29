package Monster;

import Main.MainClass;
import Main.SprideSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster {

    private double xPOS;
    private double yPOS;

    private BufferedImage monster;

    public Monster(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());

        monster = sprite.grabImage(3, 1, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics graphic) {
        graphic.drawImage(monster, (int) xPOS, (int) yPOS, null);
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }

}

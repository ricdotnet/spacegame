package Monster;

import Main.MainClass;
import Images.SprideSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster {

    private double xPOS;
    private double yPOS;

    private final BufferedImage monster;

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

        graphic.setColor(Color.black);
        Font small = new Font("Monospace", Font.BOLD, 14);
        graphic.setFont(small);
        graphic.drawString(String.valueOf(xPOS), (int) xPOS, (int) yPOS - 5);
    }

    public double getyPOS() {
        return yPOS;
    }
    public void setyPOS(double yPOS) {
        this.yPOS = getyPOS() + yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }
    public void setxPOS(double xPOS) { //bit pointless unless to move also horizontal
        this.xPOS = xPOS;
    }

}

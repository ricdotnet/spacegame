package Player;

import Images.SprideSheet;
import Main.MainClass;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heart {

    private double xPOS;
    private double yPOS;

    private BufferedImage heart;

    public Heart(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getIconsSheet());
        heart = sprite.grabIcon(1, 1, 16, 16);
    }

    public void tick() {

    }

    public void render(Graphics graphic) {
        graphic.drawImage(heart, (int) xPOS, (int) yPOS, null);
    }

    public double getyPOS() {
        return yPOS;
    }
    public double getxPOS() {
        return xPOS;
    }

}

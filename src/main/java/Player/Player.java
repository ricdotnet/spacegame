package Player;

import Images.SprideSheet;
import Main.MainClass;
import Util.Colors;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    private double xPOS;
    private double yPOS;

    private double velX;
    private double velY;

    private double rotation = 0;

    private BufferedImage player;

    public Player(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());

        player = sprite.grabImage(1, 1, 32, 32);
    }

    public void tick() {
        xPOS += velX;
        yPOS += velY;

        checkWallCollision();
    }

    public void render(Graphics2D graphic) {
        graphic.rotate(Math.toRadians(rotation), (int) xPOS - 16, (int) yPOS - 16);
        graphic.drawImage(player, (int) xPOS, (int) yPOS, null);
    }

    public void checkWallCollision() {
        if(xPOS <= 0) {
            xPOS = 0;
        }
        if(xPOS >= (MainClass.WIDTH * MainClass.SCALE) - 32) {
            xPOS = (MainClass.WIDTH * MainClass.SCALE) - 32;
        }
        if(yPOS <= 0) {
            yPOS = 0;
        }
        if(yPOS >= (MainClass.HEIGHT * MainClass.SCALE) - 32) {
            yPOS = (MainClass.HEIGHT * MainClass.SCALE) - 32;
        }
    }

    /*
    Position setters and getters
     */
    public double getxPOS() {
        return xPOS;
    }
    public double getyPOS() {
        return yPOS;
    }
    public void setxPOS(double xPOS) {
        this.xPOS = xPOS;
    }
    public void setyPOS(double yPOS) {
        this.yPOS = yPOS;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

}

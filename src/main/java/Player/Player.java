package Player;

import Images.SprideSheet;
import Main.MainClass;
import Util.Colors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class Player {

    private double xPOS;
    private double yPOS;
    private double rotation;

    private double bulletXpos = 0;
    private double bulletYpos = 0;

    private double velX;
    private double velY;
    private int vel;

    private BufferedImage player;

    public Player(double xPOS, double yPOS, double rotation, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;
        this.rotation = rotation;

        SprideSheet sprite = new SprideSheet(main.getSpriteSheet());

        player = sprite.grabImage(1, 1, 32, 32);
    }

    public void tick() {
//        xPOS += velX;
//        yPOS += velY;

        if(vel != 0)
            move();

        checkWallCollision();
    }

    public void move() {
        double angle = getRotation();
        this.xPOS += Math.sin(angle) * 2;
        this.yPOS += Math.sin(angle-90) * 2;
    }

    public void render(Graphics graphic) {
        Graphics2D g2d = (Graphics2D) graphic;

        g2d.rotate(getRotation(), xPOS+16, yPOS+16);
        g2d.setColor(Colors.LIGHT_BLACK);
        g2d.fillRect((int) xPOS, (int) yPOS, 32, 32);
//        g2d.scale(2, 2);
        g2d.drawImage(player, (int) xPOS, (int) yPOS, null);

        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(getRotation()), (int) xPOS, (int) yPOS-16);

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
    public void setVel(int vel) {
        this.vel = vel;
    }

    public void setRotation(double rotation) {
        this.rotation += rotation;
    }
    public void setRotationMouse(double rotation) {
        this.rotation = rotation;
    }
    public double getRotation() {
        DecimalFormat newRotation = new DecimalFormat("0.00");
        return Double.parseDouble(newRotation.format(rotation));
//        return rotation;
    }

    public void setBulletXpos(double bulletXpos) {
        this.bulletXpos = bulletXpos;
    }
    public double getBulletXpos() {
        return bulletXpos;
    }

    public void setBulletYpos(double bulletYpos) {
        this.bulletYpos = bulletYpos;
    }
    public double getBulletYpos() {
        return bulletYpos;
    }
}

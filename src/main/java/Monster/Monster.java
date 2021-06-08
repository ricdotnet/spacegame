package Monster;

import Main.Colors;
import Main.GameVars;
import Main.MainClass;
import Images.SprideSheet;
import Main.Time;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster {

    GameVars gameVars = new GameVars();

    private double xPOS;
    private double yPOS;

    // TODOs -> implement monster life and types. Tough monsters will have to be shot twice and have 2% probability of spawning <- deprecated
    private final String monsterObject;
    private Integer monsterHearts; //default number of hearts is 1
    private final Integer monsterMaxHearts;
    private String monsterType;
    private Integer healthBar = 32; //will decrease based on monster type

    private final BufferedImage monster;

    public Monster(double xPOS, double yPOS, MainClass main) {
        this.xPOS = xPOS;
        this.yPOS = yPOS;
        this.monsterObject = gameVars.monsterObject();
        setMonsterType();
        this.monsterHearts = Integer.parseInt(monsterObject.substring(monsterObject.length() - 2, monsterObject.length() - 1));
        this.monsterMaxHearts = monsterHearts;

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
//        graphic.drawString(String.valueOf(xPOS), (int) xPOS, (int) yPOS - 5);
//        graphic.drawString(getMonsterType(), (int) xPOS, (int) yPOS - 5);

        graphic.drawString(getMonsterType() + " -> " + getMonsterHearts(), (int) xPOS, (int) yPOS - 5);
        healthBar(graphic, (int) xPOS, (int) yPOS, healthBar, monsterHearts);
    }

    private void healthBar(Graphics g, int xPOS, int yPOS, Integer healthBar, Integer heartsLeft) {

        if(heartsLeft > monsterMaxHearts*0.6) {
            g.setColor(Colors.BRIGHT_GREEN);
            System.out.println("full");
        }

        if(heartsLeft > monsterMaxHearts*0.25 && heartsLeft < monsterMaxHearts*0.6) {
            g.setColor(Colors.ORANGE);
            System.out.println("half full");
        }

        if(heartsLeft < monsterMaxHearts*0.25) {
            g.setColor(Colors.BLOOD_RED);
            System.out.println("very weak");
        }

        g.drawRect(xPOS, yPOS + 35, 32, 2);
        g.fillRect(xPOS, yPOS + 35, healthBar, 2);
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

    public void setMonsterType() {
        this.monsterType = monsterObject.substring(1, monsterObject.length() - 4);
    }
    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterHearts() {
        this.monsterHearts = monsterHearts - 1;
        setHealthBar();
    }
    public Integer getMonsterHearts() {
        return monsterHearts;
    }

    public void setHealthBar() {
        this.healthBar = healthBar - (32/Integer.parseInt(monsterObject.substring(monsterObject.length() - 2, monsterObject.length() - 1)));
    }

}

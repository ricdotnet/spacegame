package Monster;

import Main.*;
import Images.SprideSheet;
import Util.Colors;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster {

    GameVars gameVars = new GameVars();
    ImageLoader imageLoader = new ImageLoader();

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

//        monster = imageLoader.loadImage("/fcporto.png");
//        monster = sprite.grabImage(3, 1, 32, 32);

        //TODO change monster icon based on type (toughness)
        switch (monsterType) {
            case "Weak":
                monster = sprite.grabImage(2, 1, 32, 32);
                break;
            case "Tough":
                monster = sprite.grabImage(3, 1, 32, 32);
                break;
            default:
                monster = sprite.grabImage(4, 1, 32, 32);
                break;
        }
    }

    public void tick() {

    }

    // INDEX VALUE IS TO TEST THE INDEX POSITION OF THE MONSTER
    public void render(Graphics graphic, int index) {
        graphic.drawImage(monster, (int) xPOS, (int) yPOS, null);

        graphic.setColor(Color.white);
        Font small = new Font("Monospace", Font.BOLD, 14);
        graphic.setFont(small);
//        graphic.drawString(String.valueOf(index), (int) xPOS, (int) yPOS + 40);
//        graphic.drawString(getMonsterType(), (int) xPOS, (int) yPOS - 5);

//        graphic.drawString(getMonsterType() + " -> " + getMonsterHearts(), (int) xPOS, (int) yPOS - 5);
        double barYPOS = yPOS - 5;
        if(yPOS < 15) {
            barYPOS = yPOS + 35;
        }
        healthBar(graphic, (int) xPOS, (int) barYPOS, healthBar, monsterHearts);

//        graphic.setColor(Colors.LIGHT_BLACK);
//        graphic.drawRect((int) xPOS, (int) yPOS, 32, 32);
//        graphic.fillRect((int) xPOS, (int) yPOS, 32, 32);
    }

    private void healthBar(Graphics g, int xPOS, int yPOS, Integer healthBar, Integer heartsLeft) {

        g.setColor(Color.BLACK);
        g.drawRect(xPOS, yPOS, 32, 3);

        if(heartsLeft > monsterMaxHearts*0.6) {
            g.setColor(Colors.BRIGHT_GREEN);
        }

        if(heartsLeft > monsterMaxHearts*0.25 && heartsLeft < monsterMaxHearts*0.6) {
            g.setColor(Colors.ORANGE);
        }

        if(heartsLeft < monsterMaxHearts*0.25) {
            g.setColor(Colors.BLOOD_RED);
        }

        g.fillRect(xPOS, yPOS, healthBar, 3);
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

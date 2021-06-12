package Sky;

import Main.GameVars;
import Main.MainClass;
import Util.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Stars {

    Util util = new Util();
    GameVars gameVars = new GameVars();

    private List<Star> starsList = new LinkedList<>();
    private int width = 5; //star width
    private int height = 5; //star height

    public void generateStars() {
        for(int i = 0; i < 50; i++) {
            starsList.add(new Star(util.setRandomX(), util.setRandomY(), gameVars.starType()));
        }
    }

    public void tick() {
//        for(Star star:starsList) {
//            if(star.getyPos() > MainClass.HEIGHT * MainClass.SCALE) {
//                starsList.remove(star);
//            } else {
//                star.setyPos((int) 1);
//            }
//        }
        for(int i = 0; i < starsList.size(); i++) {
            if(starsList.get(i).getyPos() > (MainClass.HEIGHT * MainClass.SCALE)) {
                starsList.remove(i);
                starsList.add(new Star(util.setRandomX(), -5, gameVars.starType()));
            } else {
                if(starsList.get(i).getType().equals("Far")) {
                    starsList.get(i).setyPos(1);
                } else if(starsList.get(i).getType().equals("Medium")) {
                    starsList.get(i).setyPos(2);
                } else if(starsList.get(i).getType().equals("Close")) {
                    starsList.get(i).setyPos(3);
                }
            }
        }
    }

    public void render(Graphics g) {
//        g.setColor(Colors.WHITE);
//        g.drawOval(x, y, w, h);
//        g.fillOval(25, 25, 25, 25);

        for(int i = 0; i < starsList.size(); i++) {
            g.setColor(gameVars.setStarColor());
            if(starsList.get(i).getType().equals("Medium")) {
                width = 3;
                height = 3;
            } else if(starsList.get(i).getType().equals("Far")) {
                width = 1;
                height = 1;
            } else if(starsList.get(i).getType().equals("Close")) {
                width = 5;
                height = 5;
            }
            g.drawOval((int) starsList.get(i).getxPos(), (int) starsList.get(i).getyPos(), width, height);
            g.fillOval((int) starsList.get(i).getxPos(), (int) starsList.get(i).getyPos(), width, height);
//            g.drawString(String.valueOf(starsList.get(i).getyPos()), (int) starsList.get(i).getxPos(), (int) starsList.get(i).getyPos());
        }

    }

}

class Star {

    private double xPos;
    private double yPos;
    private String type; //close, medium, far (close moves faster than medium and medium faster than far) for starter only close type

    public Star(double xPos, double yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }
    public double getxPos() {
        return xPos;
    }

    public void setyPos(double yPos) {
        this.yPos += yPos;
    }
    public double getyPos() {
        return yPos;
    }

    public String getType() {
        return type;
    }
}
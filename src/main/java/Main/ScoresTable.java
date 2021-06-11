package Main;

import Util.Label;

import javax.swing.*;
import java.awt.*;

public class ScoresTable extends JPanel {

    Scores scores = new Scores();

    public ScoresTable() {

        setSize(200, 200);
        setLocation(50, 50);
        setBackground(Color.white);

        scores.getScores();
        for(PlayerScore score:scores.printScores()) {
            System.out.println(score.getName() + " : " + score.getScore() + " : " + score.getDifficulty());
        }

        Label name = new Label("Player1", scores.printScores().get(0).getName() + " :: " + scores.printScores().get(0).getScore());
        add(name);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawScores(g);
//    }

//    private void drawScores(Graphics g) {
//        g.setColor(Color.BLACK);
//        Font small = new Font("Monospace", Font.BOLD, 18);
//
//        g.drawString(scores.printScores().get(0).getName(), 5, 15);
//        g.drawString(String.valueOf(scores.printScores().get(0).getScore()), 55, 15);
//    }

//    private void newScores(List<Scores> list) {
//
//    }

}

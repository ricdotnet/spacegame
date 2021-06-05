package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;
    Scores scores = new Scores();
    MainClass click = new MainClass(); //for button click purposes

    public ButtonClick(MainClass main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        String name = button.getName();

        // start button command
        if(e.getActionCommand().equals("Start")) {
            main.startGame(e);
        }

        // quit button command
        if(e.getActionCommand().equals("Quit")) {
            main.quitGame(e);
        }

        // scores button command
        if(e.getActionCommand().equals("Scores")) {
            System.out.println(scores.printScores());
        }

        // confirm name button
        if(e.getActionCommand().equals("Confirm")) {
            main.restart();
        }

        if(name.equals("BLOOD_RED")) {
            click.changeBg(Colors.BLOOD_RED);
            System.out.println("red....");
        }

    }
}

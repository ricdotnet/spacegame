package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;
    Scores scores = new Scores();
    MainClass mainClass = new MainClass(); //for button click purposes

    public ButtonClick(MainClass main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        String name = button.getName();

        // start button command
        if(name.equals("START_GAME")) {
            mainClass.startGame(e);
        }

        // quit button command
        if(name.equals("QUIT_GAME")) {
            mainClass.quitGame(e);
        }

        // confirm name button
        if(name.equals("CONFIRM_NAME")) {
            main.restart();
        }

    }
}

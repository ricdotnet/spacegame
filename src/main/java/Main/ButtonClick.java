package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;

    public ButtonClick(MainClass main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // start button command
        if(e.getActionCommand().equals("Start")) {
            main.startGame(e);
        }

        // quit button command
        if(e.getActionCommand().equals("Quit")) {
            main.quitGame(e);
        }

    }
}

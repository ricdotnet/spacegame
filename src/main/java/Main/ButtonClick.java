package Main;

import Player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;
    Scores scores = new Scores();
    AskName name = new AskName(main);
    Player player = new Player();

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
            //main.quitGame(e);
            System.out.println(player.getPlayerName());
        }

        // scores button command
        if(e.getActionCommand().equals("Scores")) {
            System.out.println(scores.printScores());
        }

        // confirm name button
        if(e.getActionCommand().equals("Confirm")) {

        }

    }
}

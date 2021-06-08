package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;
    MainClass mainClass = new MainClass(); //for button click purposes
    AskName askName = new AskName();
    GameVars gameVars = new GameVars();

    public ButtonClick(MainClass main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        String name = button.getName();

        // start button command
        if(name.equals("START_GAME")) {
            gameVars.setIsTesting(false); //always make sure testing mode is off
            mainClass.startGame(e);
        }

        // quit button command
        if(name.equals("QUIT_GAME")) {
            mainClass.quitGame(e);
        }

        // confirm name button
        if(name.equals("CONFIRM_NAME")) {
            if(!askName.nameField.getText().isEmpty()) {
                askName.nameField.setBackground(Colors.BLOOD_RED);
            } else {
                main.restart();
            }
        }

        // difficulty change button
        if(name.equals("DIFFICULTY_CHANGE")) {
            switch (gameVars.getDifficulty()) {
                case ("Easy"):
                    gameVars.setDifficulty("Medium");
                    break;
                case ("Medium"):
                    gameVars.setDifficulty("Hard");
                    break;
                default:
                    gameVars.setDifficulty("Easy");
            }

            MainMenu.setGameDifficulty(gameVars.getDifficulty());
        }

        /**
         * Test button
         * During testing mode monsters don't shoot
         */
        if(name.equals("TEST_GAME")) {
            gameVars.setIsTesting(true); //set testing mode on
            mainClass.startGame(e);
//            String monster = gameVars.monsterObject();
//            String monsterType = monster.substring(1, monster.length() - 4);
//            String monsterHearts = monster.substring(monster.length() - 2, monster.length() - 1);
//
//            System.out.println(monsterType + " :: " + monsterHearts);
        }

    }
}

package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClick implements ActionListener {

    MainClass main;
    MainClass mainClass = new MainClass(); //for button click purposes
    AskName askName = new AskName(main);
    GameVars gameVars = new GameVars();
    MainMenu mainMenu = new MainMenu();

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
            if(!askName.nameField.getText().isEmpty()) {
                askName.nameField.setBackground(Colors.BLOOD_RED);
            } else {
                main.restart();
            }
        }

        // change difficulty button
        if(name.equals("CHANGE_DIFFICULTY")) {

            switch (gameVars.getDifficulty()) {
                case "Easy":
                    gameVars.setDifficulty("Medium");
                    break;
                case "Medium":
                    gameVars.setDifficulty("Hard");
                    break;
                case "Hard":
                    gameVars.setDifficulty("Easy");
                    break;
            }

            /**
             * difficulty not working
             */
            mainMenu.difficulty.setText("sdfsdfsdf");
        }

    }
}

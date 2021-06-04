package Main;

import Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class AskName extends JDialog {

    private JLabel nameLabel;
    private JTextField nameField;
    private JButton confirmButton;

    Player player = new Player();
    MainClass mainClass;
    Scores scores = new Scores();

    public AskName(MainClass main) {

        nameLabel = new JLabel();
        nameField = new JTextField();
        confirmButton = new JButton();

        setTitle("Player Name");
        setLocationRelativeTo(main);
        setSize(275, 120);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(0);

        nameLabel.setText("Enter your name");
        nameField.setText("");
        confirmButton.setText("Confirm");

        nameLabel.setSize(250, 20);
        nameField.setSize(250, 20);
        confirmButton.setSize(100, 20);

        nameLabel.setLocation(5, 5);
        nameField.setLocation(5, 30);
        confirmButton.setLocation(154, 55);

        add(nameLabel);
        add(nameField);
        add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!nameField.getText().isEmpty()) {
                    player.setPlayerName(nameField.getText());
                    nameField.setBackground(Color.white);
                    nameField.setText("");
                    setVisible(false);

                    scores.addScore(main.score);

                    return;
                }

                nameField.setBackground(Color.red);
            }
        });
    }

}

package Main;

import javax.swing.*;

public class AskName extends JDialog {

    public JLabel nameLabel;
    public JTextField nameField;
    public JButton confirmButton = new JButton();

    public AskName() {

        nameLabel = new JLabel();
        nameField = new JTextField();

        setTitle("Player Name");
        setLocationRelativeTo(null);
        setSize(275, 120);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(0);

        nameLabel.setText("Enter your name");
        nameField.setText("");
        confirmButton.setText("Confirm");
        confirmButton.setName("CONFIRM_NAME");

        nameLabel.setSize(250, 20);
        nameField.setSize(250, 20);
        confirmButton.setSize(100, 20);

        nameLabel.setLocation(5, 5);
        nameField.setLocation(5, 30);
        confirmButton.setLocation(154, 55);

        add(nameLabel);
        add(nameField);
        add(confirmButton);
    }

}

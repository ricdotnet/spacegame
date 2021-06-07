package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {

    MainClass main;
    ImageLoader image = new ImageLoader();
    ScoresTable scoresTable = new ScoresTable();
    GameVars gameVars = new GameVars();

    private BufferedImage background;

    private final JButton startButton = new JButton();
    private final JButton quitButton = new JButton();
    private final JButton changeDifficulty = new JButton();

    private static final JLabel gameDifficulty = Labels.gameDifficulty;

    public MainMenu() {

        //set main menu options
        setPreferredSize(new Dimension(MainClass.WIDTH * MainClass.SCALE, MainClass.HEIGHT * MainClass.SCALE));
        setSize((MainClass.WIDTH * MainClass.SCALE), (MainClass.HEIGHT * MainClass.SCALE));
        setLocation(0, 0);

        setOpaque(true);
        setLayout(null);

        //set component texts
        startButton.setText("Start");
        startButton.setName("START_GAME");
        quitButton.setText("Quit");
        quitButton.setName("QUIT_GAME");
        changeDifficulty.setText("Change Difficulty");
        changeDifficulty.setName("DIFFICULTY_CHANGE");
        gameDifficulty.setText(gameVars.getDifficulty());

        //set component sizes
        startButton.setSize(100, 30);
        quitButton.setSize(100, 30);
        changeDifficulty.setSize(140, 30);
        gameDifficulty.setSize(100, 30);

        //set component positions
        startButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) - 105, 10);
        quitButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) + 5, 10);
        changeDifficulty.setLocation((MainClass.WIDTH * 2) - 165, (MainClass.HEIGHT * MainClass.SCALE) - 45);
        gameDifficulty.setLocation(changeDifficulty.getX(), changeDifficulty.getY() - 40);

        //set component colors
        gameDifficulty.setForeground(Colors.WHITE);

        //add buttons to jPanel
        add(startButton);
        add(quitButton);
        add(scoresTable);
        add(changeDifficulty);
        add(gameDifficulty);

        //action listeners
        startButton.addActionListener(new ButtonClick(main));
        quitButton.addActionListener(new ButtonClick(main));
        changeDifficulty.addActionListener(new ButtonClick(main));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage(), 0, 0, this);
    }

    public static void setGameDifficulty(String difficulty) {
        gameDifficulty.setText(difficulty);
    }

    private BufferedImage backgroundImage() {
        return image.loadImage("/spaceGame.png");
    }

}

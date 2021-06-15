package Main;

import Util.Colors;
import Util.Label;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {

    MainClass main;
    ImageLoader image = new ImageLoader();
    ScoresTable scoresTable;
    GameVars gameVars = new GameVars();

    private BufferedImage background;

    private final JButton startButton = new JButton();
    private final JButton quitButton = new JButton();
    private final JButton changeDifficulty = new JButton();

    private final JButton testButton = new JButton();

    private static Label gameDifficulty;

    public MainMenu() {
        if(scoresTable == null)
            scoresTable = new ScoresTable();
//        setNewScores();

        //set main menu options
        setPreferredSize(new Dimension(MainClass.WIDTH * MainClass.SCALE, MainClass.HEIGHT * MainClass.SCALE));
        setSize((MainClass.WIDTH * MainClass.SCALE), (MainClass.HEIGHT * MainClass.SCALE));
        setLocation(0, 0);

        setOpaque(true);
        setLayout(null);

        gameDifficulty = new Label("Game Difficulty", gameVars.getDifficulty());

        //set component texts
        startButton.setText("Start");
        startButton.setName("START_GAME");
        quitButton.setText("Quit");
        quitButton.setName("QUIT_GAME");
        changeDifficulty.setText("Change Difficulty");
        changeDifficulty.setName("DIFFICULTY_CHANGE");
//        gameDifficulty.setText(gameVars.getDifficulty());
        testButton.setText("Test");
        testButton.setName("TEST_GAME");

        //set component sizes
        startButton.setSize(100, 30);
        quitButton.setSize(100, 30);
        changeDifficulty.setSize(140, 30);
        gameDifficulty.setSize(100, 30);
        testButton.setSize(100, 30);

        //set component positions
        startButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) - 105, 10);
        quitButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) + 5, 10);
        changeDifficulty.setLocation((MainClass.WIDTH * MainClass.SCALE) - 165, (MainClass.HEIGHT * MainClass.SCALE) - 45);
        gameDifficulty.setLocation(changeDifficulty.getX(), changeDifficulty.getY() - 40);
        testButton.setLocation(15, getHeight() - 45);

        //set component colors
        gameDifficulty.setForeground(Colors.WHITE);

        //add buttons to jPanel
        add(startButton);
        add(quitButton);
        add(scoresTable);
        add(changeDifficulty);
        add(gameDifficulty);
        add(testButton);

        //action listeners
        startButton.addActionListener(new ButtonClick());
        quitButton.addActionListener(new ButtonClick());
        changeDifficulty.addActionListener(new ButtonClick());
        testButton.addActionListener(new ButtonClick());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage(), 0, 0, this);
    }

    public static void setGameDifficulty(String difficulty) {
        gameDifficulty.setLabelText(difficulty);
    }

    private BufferedImage backgroundImage() {
        return image.loadImage("/spaceGame.png");
    }

}

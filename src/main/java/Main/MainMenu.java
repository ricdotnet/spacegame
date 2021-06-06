package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {

    MainClass main = new MainClass();
    ImageLoader image = new ImageLoader();
    ScoresTable scoresTable = new ScoresTable();
    GameVars gameVars = new GameVars();

    private BufferedImage background;

    private final JButton startButton = new JButton();
    private final JButton quitButton = new JButton();
    private final JButton changeDifficulty = new JButton();
    public final JLabel difficulty = new JLabel();

    public MainMenu() {}

    public MainMenu(int HEIGHT) {

        background = image.loadImage("/spaceGame.png");

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
        changeDifficulty.setName("CHANGE_DIFFICULTY");
        difficulty.setForeground(Colors.WHITE);
        difficulty.setText(gameVars.getDifficulty());

        //set component sizes
        startButton.setSize(100, 30);
        quitButton.setSize(100, 30);
        changeDifficulty.setSize(140, 30);
        difficulty.setSize(140, 30);

        //set component positions
        startButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) - 105, 10);
        quitButton.setLocation(((MainClass.WIDTH * MainClass.SCALE) / 2) + 5, 10);
        changeDifficulty.setLocation((MainClass.WIDTH * 2) - 165, (MainClass.HEIGHT * MainClass.SCALE) - 45);
        difficulty.setLocation((MainClass.WIDTH * 2) - 165, (MainClass.HEIGHT * MainClass.SCALE) - 80);

        //add buttons to jPanel
        add(startButton);
        add(quitButton);
        add(scoresTable);
        add(changeDifficulty);
        add(difficulty);

        //action listeners
        startButton.addActionListener(new ButtonClick(main));
        quitButton.addActionListener(new ButtonClick(main));
        changeDifficulty.addActionListener(new ButtonClick(main));

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

}

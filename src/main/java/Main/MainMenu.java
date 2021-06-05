package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {

    MainClass main = new MainClass();
    ImageLoader image = new ImageLoader();
    ScoresTable scoresTable = new ScoresTable();

    private BufferedImage background;

    private final JButton startButton = new JButton();
    private final JButton quitButton = new JButton();

    public MainMenu(int WIDTH, int HEIGHT, int SCALE) {

        background = image.loadImage("/spaceGame.png");

        //set main menu options
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setSize((WIDTH * SCALE), (HEIGHT * SCALE));
        setLocation(0, 0);

        setOpaque(true);
        setLayout(null);

        //set button texts
        startButton.setText("Start");
        startButton.setName("START_GAME");
        quitButton.setText("Quit");
        quitButton.setName("QUIT_GAME");

        //set button sizes
        startButton.setSize(100, 30);
        quitButton.setSize(100, 30);

        //set button positions
        startButton.setLocation(((WIDTH * SCALE) / 2) - 105, 10);
        quitButton.setLocation(((WIDTH * SCALE) / 2) + 5, 10);

        //add buttons to jPanel
        add(startButton);
        add(quitButton);
        add(scoresTable);

        //action listeners
        startButton.addActionListener(new ButtonClick(main));
        quitButton.addActionListener(new ButtonClick(main));

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

}

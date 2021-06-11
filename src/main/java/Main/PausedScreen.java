package Main;

import java.awt.*;

public class PausedScreen {

    private final int WIDTH = MainClass.WIDTH;
    private final int HEIGHT = MainClass.HEIGHT;

    public void pausedScreen(Graphics g) {
        g.setColor(Colors.LIGHT_BLACK);
        g.fillRect(WIDTH-(WIDTH/2), HEIGHT-(HEIGHT/2), WIDTH, HEIGHT);

        pausedText(g);
    }

    private void pausedText(Graphics g) {
        g.setColor(Colors.WHITE);
        Font title = new Font("Monospace", Font.BOLD, 24);
        Font info = new Font("Monospace", Font.PLAIN, 18);

        g.setFont(title);
        g.drawString("Paused...", WIDTH-50, HEIGHT-20);

        g.setFont(info);
        g.drawString("Press R to restart the game.", WIDTH-110, HEIGHT+20);
        g.drawString("(you will lose all your score)", WIDTH-110, HEIGHT+40);
    }

}

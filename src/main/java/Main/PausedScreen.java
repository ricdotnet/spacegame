package Main;

import Util.Colors;

import java.awt.*;

public class PausedScreen {

    private final int WIDTH = MainClass.WIDTH;
    private final int HEIGHT = MainClass.HEIGHT;

    public void pausedScreen(Graphics g) {
        g.setColor(Colors.LIGHT_BLACK);
        g.fillRect((WIDTH/2)/2, (HEIGHT/2)/2, WIDTH/2, HEIGHT/2);

        pausedText(g);
    }

    private void pausedText(Graphics g) {
        g.setColor(Colors.WHITE);
        Font title = new Font("Monospace", Font.BOLD, 24);
        Font info = new Font("Monospace", Font.PLAIN, 18);

        g.setFont(title);
        g.drawString("Paused...", (WIDTH/2)-50, (HEIGHT/2)-20);

        g.setFont(info);
        g.drawString("Press R to restart the game.", (WIDTH/2)-110, (HEIGHT/2)+20);
        g.drawString("(you will lose all your score)", (WIDTH/2)-110, (HEIGHT/2)+40);
    }

}

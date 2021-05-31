package Main;

import java.awt.*;

public class MainMenu extends Canvas implements Runnable {

    public void init() {
        MainMenu menu = new MainMenu();
        menu.setPreferredSize(new Dimension(250, 250));
        menu.setMaximumSize(new Dimension(250, 250));
        menu.setMinimumSize(new Dimension(250, 250));
        menu.setBackground(Color.WHITE);
    }

    @Override
    public void run() {
        init();
    }
}

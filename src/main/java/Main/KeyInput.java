package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    MainClass main;

    public KeyInput(MainClass main) {
        this.main = main;
    }

    public void keyPressed(KeyEvent e) {
        main.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        main.keyReleased(e);
    }

}

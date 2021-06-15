package Main;

import Player.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    MainClass main;
    Player player;
    PlayerEvents playerEvents;

    public KeyInput(MainClass main, Player player, PlayerEvents playerEvents) {
        this.main = main;
        this.player = player;
        this.playerEvents = playerEvents;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(5);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setVelX(-5);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setVelY(-5);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setVelY(5);
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            playerEvents.playerShoot();
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            main.pauseUnpause();
        }

        if(e.getKeyCode() == KeyEvent.VK_R) {
            main.resetOnPause();
        }

    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setVelX(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setVelY(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setVelY(0);
        }
    }

}

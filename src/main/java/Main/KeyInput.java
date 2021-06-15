package Main;

import Player.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput extends MainClass implements KeyListener {

    Player player;
    PlayerEvents playerEvents;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

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

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setPlayerEvents(PlayerEvents playerEvents) {
        this.playerEvents = playerEvents;
    }

}

package Main;

import Player.*;
import Sky.Asteroids;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput extends MainClass implements MouseListener, MouseMotionListener {

//    MainClass main;
    Player player;
    Asteroids asteroids = new Asteroids(this);

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        for(int i = 0; i < asteroids.getAsteroidList().size(); i++) {
            if ((mouseEvent.getX() > asteroids.getAsteroidList().get(i).getxPos() && mouseEvent.getX() < asteroids.getAsteroidList().get(i).getxPos() + 32)
                    && (mouseEvent.getY() > asteroids.getAsteroidList().get(i).getyPos() && mouseEvent.getY() < asteroids.getAsteroidList().get(i).getyPos() + 32)) {
                asteroids.removeAsteroid(asteroids.getAsteroidList().get(i));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

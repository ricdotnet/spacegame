package Main;

import Player.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs extends MainClass implements MouseMotionListener, MouseListener {

    Player player;
    PlayerEvents playerEvents;

    public MouseInputs(Player player, PlayerEvents playerEvents) {
        this.player = player;
        this.playerEvents = playerEvents;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
//        System.out.println((mouseEvent.getX() - (player.getxPOS() + 16)) + " - " + (mouseEvent.getY() - player.getyPOS()));
//        System.out.println(Math.atan((mouseEvent.getY() - player.getyPOS()) / (mouseEvent.getX() - (player.getxPOS() + 16))));
//        player.setRotation(Math.atan((mouseEvent.getY() - player.getyPOS()) / (mouseEvent.getX() - (player.getxPOS() + 16))) - Math.PI / 2);
        player.setRotationMouse(Math.atan2((player.getyPOS() - (mouseEvent.getY())), (player.getxPOS() - mouseEvent.getX())) - Math.PI / 2);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        playerEvents.playerShoot();
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
}

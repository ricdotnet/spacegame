package Player;

import Bomb.*;
import Bullet.*;
import Main.*;

public class PlayerEvents {

    MainClass main;

    GameVars gameVars = new GameVars();
    SoundLoader soundLoader = new SoundLoader();

    Player player;

    BulletController bulletController = new BulletController(main);

    Bomb bomb;
    BombController bombController = new BombController(main);

    HeartController heartController = new HeartController(main);

    public PlayerEvents(MainClass main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void playerShoot() {

        switch (gameVars.getDifficulty()) {
            case ("Easy"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 10) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, main));
                    soundLoader.playSound("/playerShoot.wav");
                }
                break;
            case ("Medium"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 5) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, main));
                    soundLoader.playSound("/playerShoot.wav");
                }
                break;
            case ("Hard"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 2) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, main));
                    soundLoader.playSound("/playerShoot.wav");
                }
                break;
        }
    }

    public void userHit() {
        for(int i = 0; i < bombController.getBombList().size(); i++) {
            bomb = bombController.getBombList().get(i);

            if(((bomb.getyPOS() > player.getyPOS() - 15) && bomb.getyPOS() < player.getyPOS()) && (bomb.getxPOS() + 20 > player.getxPOS() && bomb.getxPOS() + 10 < player.getxPOS() + 32)) {
                bombController.removeBomb(bomb);
                heartController.removeHeart();
            }
        }
    }

}

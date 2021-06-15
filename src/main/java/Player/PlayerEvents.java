package Player;

import Bomb.*;
import Bullet.*;
import Main.*;
import Sky.Asteroid;
import Sky.Asteroids;

public class PlayerEvents {

    private boolean shotIsValid = false;

    MainClass main;

    GameVars gameVars = new GameVars();
    SoundLoader soundLoader = new SoundLoader();

    Player player;

    BulletController bulletController = new BulletController(main);

    Bomb bomb;
    BombController bombController = new BombController(main);

    Asteroid asteroid;
    Asteroids asteroids = new Asteroids(main);

    HeartController heartController = new HeartController(main);

    public PlayerEvents(MainClass main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void playerShoot() {

        switch (gameVars.getDifficulty()) {
            case ("Easy"):
                //allow only 2 bullets on the screen at once
                addBullet(10);
                break;
            case ("Medium"):
                //allow only 2 bullets on the screen at once
                addBullet(5);
                break;
            case ("Hard"):
                //allow only 2 bullets on the screen at once
                addBullet(2);
                break;
        }
    }

    private void addBullet(int loopSize) {
        if(bulletController.getBulletList().size() < loopSize) {
            bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, main));
            soundLoader.playSound("/playerShoot.wav");
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

    public void asteroidCollision() {
        for (int i = 0; i < asteroids.getAsteroidList().size(); i++) {
            if (((asteroids.getAsteroidList().get(i).getxPos() > player.getxPOS() && asteroids.getAsteroidList().get(i).getxPos() < player.getxPOS() + 32)
                    || (asteroids.getAsteroidList().get(i).getxPos() + 32 > player.getxPOS() && asteroids.getAsteroidList().get(i).getxPos() + 32 < player.getxPOS() + 32))
                    && ((asteroids.getAsteroidList().get(i).getyPos() + 32 > player.getyPOS() && asteroids.getAsteroidList().get(i).getyPos() + 32 < player.getyPOS() + 32)
                    || (asteroids.getAsteroidList().get(i).getyPos() > player.getyPOS() && asteroids.getAsteroidList().get(i).getyPos() < player.getyPOS() + 32))) {
                asteroids.removeAsteroid(asteroids.getAsteroidList().get(i));
                heartController.removeHeart();
            }
        }
    }

}

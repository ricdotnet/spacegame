/**
 * MONSTER EVENTS CLASS
 * HERE WILL BE ALL EVENTS RELATED TO MONSTERS
 * COLLISION CHECKING, SPAWNING AND MONSTER SHOOTING
 */

package Monster;

import Bomb.*;
import Bullet.*;
import Main.GameVars;
import Main.MainClass;
import Main.SoundLoader;
import Player.*;
import Util.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonsterEvents {

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    private int SPAWN_SIZE = 0;

    MainClass main;
    SoundLoader soundLoader = new SoundLoader();
    PlayerVars playerVars = new PlayerVars();
    GameVars gameVars = new GameVars();
    Util util = new Util();
    Time time = new Time();

    Bullet bullet;
    BulletController bulletController = new BulletController(main);

    Monster monster;
    MonsterController monsterController = new MonsterController(main);

    Explosion explosion;
    ExplosionController explosionController = new ExplosionController(main);

    BombController bombController = new BombController(main);

    Player player;
    HeartController heartController = new HeartController(main);

    public MonsterEvents(MainClass main, Player player) {
        this.main = main;
        this.player = player;
    }

    public void monsterKilled() {
        if((bulletController.getBulletList().size() > 0)) {
            for (int i = 0; i < monsterController.getMonsterList().size(); i++) {
                for (int j = 0; j < bulletController.getBulletList().size(); j++) {
                    monster = monsterController.getMonsterList().get(i);
                    bullet = bulletController.getBulletList().get(j);

                    if ((bullet.getxPOS() + 20 > monster.getxPOS() && bullet.getxPOS() + 10 < monster.getxPOS() + 32) && (bullet.getyPOS() > monster.getyPOS() && bullet.getyPOS() < monster.getyPOS() + 16)) {

                        if (monster.getMonsterHearts() != 1) {
                            monster.setMonsterHearts();
                            soundLoader.playSound("/monsterHit.wav");
                            bulletController.removeBullet(bullet);
                            return;
                        }

                        removeEntities(monster, bullet); //remove both monster and bullet
                        addExplosion(monster.getxPOS(), monster.getyPOS()); //show explosion
                        spawnNewMonsters(); //set a spawn bubble size after the monster dies

                        playerVars.setPlayerScore(); //increase score by 1

                        soundLoader.playSound("/enemyExplode.wav"); //play explosion sound
                    }
                }
            }
        }
    }

    /**
     * ACTION FOR THE MONSTER SHOOTING
     */
    public void addBomb() {

        Monster randomMonster = monsterController.getMonsterList().get(0);

        if(monsterController.getMonsterList().size() > 1) {
            for(int i = 0; i < monsterController.getMonsterList().size(); i++) {

                //if the next monster position is closer to the player then assign a new monster
                if(Math.abs(randomMonster.getxPOS() - player.getxPOS()) > Math.abs(monsterController.getMonsterList().get(i+1).getxPOS() - player.getxPOS())) {
                    randomMonster = monsterController.getMonsterList().get(i+1);
//                    System.out.println("found a closer monster"); //message to say a closer monster has been found
                }

                //break the loop when there are no more monsters to count with
                if(i+1 == monsterController.getMonsterList().size() - 1) {
                    break;
                }

            }
        }

        /**
         * Let the monster shoot only if there are no bombs in the screen
         * Will change this with the difficulty level
         */
        if(bombController.getBombList().size() == 0 && randomMonster != null && !gameVars.getIsTesting()) {
            bombController.addBomb(new Bomb(randomMonster.getxPOS(), randomMonster.getyPOS() + 32, main));
            soundLoader.playSound("/enemyShoot.wav");
        }
    }

    /**
     * SET A SPAWN SIZE AND SPAWN MONSTERS WHEN A MONSTER DIES
     */
    private void spawnNewMonsters() {
        if (util.chance() < 0.01) {
            SPAWN_SIZE = 6;
        } else if (util.chance() < 0.15) {
            SPAWN_SIZE = 3;
        } else if (util.chance() < 0.30) {
            SPAWN_SIZE = 2;
        } else {
            SPAWN_SIZE = 1;
        }

        for (int k = 0; k < SPAWN_SIZE; k++) {
            double randomY = util.setRandomY();
            if(randomY > (MainClass.HEIGHT/2)) {
                randomY -= 250;
            }
            monsterController.addMonster(new Monster(util.setRandomX(), randomY, main));
        }
    }

    /**
     * SHOW EXPLOSION WHEN A MONSTER DIES
     * @param tempX
     * @param tempY
     */
    private void addExplosion(double tempX, double tempY) {
//        explosion = new Explosion(tempX, tempY, main);
        explosionController.addExplosion(new Explosion(tempX, tempY, time.msNow(), main));
        service.schedule(removeExplosion, 200, TimeUnit.MILLISECONDS);
    }

    /**
     * REMOVE ENTITIES
     */
    private void removeEntities(Monster monster, Bullet bullet) {
        monsterController.removeMonster(monster);
        bulletController.removeBullet(bullet);
    }

    /**
     * CHECK WHEN THE MONSTER IS OUT OF THE SCREEN
     */
    public void monsterOut() {
        for(int i = 0; i < monsterController.getMonsterList().size(); i++) {
            monster = monsterController.getMonsterList().get(i);
            if(monster.getyPOS() > (MainClass.HEIGHT * MainClass.SCALE)) {
                monsterController.removeMonster(monster);
                heartController.removeHeart();
            }
        }
    }

    Runnable removeExplosion = new Runnable() {
        @Override
        public void run() {
            explosion = null;
        }
    };
}

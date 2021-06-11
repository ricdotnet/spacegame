/**
 * MONSTER EVENTS CLASS
 * HERE WILL BE ALL EVENTS RELATED TO MONSTERS
 * COLLISION CHECKING, SPAWNING AND MONSTER SHOOTING
 */

package Monster;

import Bullet.Bullet;
import Bullet.BulletController;
import Main.MainClass;
import Main.SoundLoader;
import Player.PlayerVars;
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
    Util util = new Util();

    Bullet bullet;
    BulletController bulletController = new BulletController(main);

    Monster monster;
    MonsterController monsterController = new MonsterController(main);

    Explosion explosion;
    ExplosionController explosionController = new ExplosionController(main);

    public MonsterEvents(MainClass main) {
        this.main = main;
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
            monsterController.addMonster(new Monster(util.setRandomX(), util.setRandomY(), main));
        }
    }

    /**
     * SHOW EXPLOSION WHEN A MONSTER DIES
     * @param tempX
     * @param tempY
     */
    private void addExplosion(double tempX, double tempY) {
//        explosion = new Explosion(tempX, tempY, main);
        explosionController.addExplosion(new Explosion(tempX, tempY, Time.msNow(), main));
        service.schedule(removeExplosion, 200, TimeUnit.MILLISECONDS);
    }

    /**
     * REMOVE ENTITIES
     */
    private void removeEntities(Monster monster, Bullet bullet) {
        monsterController.removeMonster(monster);
        bulletController.removeBullet(bullet);
    }

    Runnable removeExplosion = new Runnable() {
        @Override
        public void run() {
            explosion = null;
        }
    };
}

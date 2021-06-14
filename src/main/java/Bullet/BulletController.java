package Bullet;

import Main.MainClass;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BulletController {

    private static List<Bullet> bulletList = new LinkedList<Bullet>();

    Bullet bullet;
    MainClass main;

    public BulletController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            if(bullet.getyPOS() < 0 || bullet.getyPOS() > MainClass.HEIGHT || bullet.getxPOS() < 0 || bullet.getxPOS() > MainClass.WIDTH) {
                removeBullet(bullet);
            }
            bullet.tick();
        }
    }

//    public void render(Graphics2D graphic) {
//        for(int i = 0; i < bulletList.size(); i++) {
//            bullet = bulletList.get(i);
//            bullet.render(graphic);
//        }
//    }

    public void addBullet(Bullet bullet) {
        bulletList.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bulletList.remove(bullet);
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

}

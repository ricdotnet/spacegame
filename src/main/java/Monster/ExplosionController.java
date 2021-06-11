package Monster;

import Main.MainClass;
import Util.Time;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ExplosionController {

    private static List<Explosion> explosionsList = new LinkedList<>();

    MainClass main;

    public ExplosionController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(Explosion explosion:explosionsList) {
            if((Time.msNow() - explosion.getExplodedIn()) >= 250) {
                removeExplosion(explosion);
            }
        }
    }

    public void render(Graphics graphic) {
        for(Explosion explosion:explosionsList) {
            explosion.render(graphic);
        }
    }

    public void addExplosion(Explosion explosion) {
        explosionsList.add(explosion);
    }

    public void removeExplosion(Explosion explosion) {
        explosionsList.remove(explosion);
    }

}

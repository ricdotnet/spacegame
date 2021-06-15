package Monster;

import Main.MainClass;
import Util.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ExplosionController {

    private static List<Explosion> explosionsList = new LinkedList<>();

    MainClass main;
    Time time = new Time();

    public ExplosionController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < explosionsList.size(); i++) {
            if((time.msNow() - explosionsList.get(i).getExplodedIn()) >= 250) {
                removeExplosion(i);
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

    public void removeExplosion(int explosion) {
        explosionsList.remove(explosion);
    }

    public List<Explosion> getExplosionsList() {
        return explosionsList;
    }

}

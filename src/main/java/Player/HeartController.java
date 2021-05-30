package Player;

import Main.MainClass;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class HeartController {

    private List<Heart> heartList = new LinkedList<Heart>();

    Heart heart;
    MainClass main;

    public HeartController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < heartList.size(); i++) {
            heart = heartList.get(i);
            heart.tick();
        }
    }

    public void render(Graphics graphic) {
        for(int i = 0; i < heartList.size(); i++) {
            heart = heartList.get(i);
            heart.render(graphic);
        }
    }

    public void addHeart(Heart heart) {
        heartList.add(heart);
    }
    public void removeHeart() {
        heartList.remove(heartList.size()-1);
    }

    public List<Heart> getHeartList() {
        return heartList;
    }

}

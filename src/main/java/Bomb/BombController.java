package Bomb;

import Main.MainClass;
import Monster.MonsterController;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BombController {

    private List<Bomb> bombList = new LinkedList<Bomb>();

    Bomb bomb;
    MainClass main;

    public BombController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < bombList.size(); i++) {
            bomb = bombList.get(i);

            if(bomb.getyPOS() >= main.getHeight()) {
                removeBomb(bomb);
            }

            bomb.tick();
        }
    }

    public void render(Graphics graphic) {
        for(int i = 0; i < bombList.size(); i++) {
            bomb = bombList.get(i);
            bomb.render(graphic);
        }
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public void removeBomb(Bomb bomb) {
        bombList.remove(bomb);
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

}

package Monster;

import Main.MainClass;
import Main.Time;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MonsterController {

    private List<Monster> monsterList = new LinkedList<Monster>();
    private boolean move = true;

    Monster monster;
    MainClass main;

    public MonsterController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            monster.tick();
            if(Time.chance() < 0.02) {
                moveMonster(monster);
            }
        }
    }

    public void render(Graphics graphic) {
        for(int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            monster.render(graphic);
        }
    }

    public void addMonster(Monster monster) {
        monsterList.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsterList.remove(monster);
    }

    public void moveMonster(Monster monster) {
        if(move) {
            monster.setyPOS(5);
        }
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

}

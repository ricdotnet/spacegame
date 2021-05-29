package Monster;

import Main.MainClass;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MonsterController {

    private List<Monster> monsterList = new LinkedList<Monster>();

    Monster monster;
    MainClass main;

    public MonsterController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            monster.tick();
        }
    }

    public void render(Graphics graphic) {
        for(int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            monster.render(graphic);
        }
    }

    public void addMonster(Monster monster) {
        if(monsterList.size() > 0) {
            monsterList.remove(0);
        }
        monsterList.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsterList.remove(monster);
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

}

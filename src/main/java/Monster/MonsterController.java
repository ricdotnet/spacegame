package Monster;

import Main.GameVars;
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
    GameVars gameVars = new GameVars();

    public MonsterController(MainClass main) {
        this.main = main;
    }

    public void tick() {
        for(int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            monster.tick();
            double chance = Time.chance();

            switch (gameVars.getDifficulty()) {
                case ("Easy"):
                    if(chance < 0.01) {
                        moveMonster(monster, 1);
                    }
                    break;
                case ("Medium"):
                    if(chance < 0.02) {
                        moveMonster(monster, 2);
                    }
                    break;
                case ("Hard"):
                    if(chance < 0.03) {
                        moveMonster(monster, 3);
                    }
                    break;
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

    public void moveMonster(Monster monster, int stepSize) {
        if(move) {
            monster.setyPOS(stepSize);
        }
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

}

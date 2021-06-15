package Main;

import Player.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer {

    MainClass main;
    Player player;

    private BufferStrategy bufferStrategy;

    public Renderer(MainClass main, Player player) {
        this.main = main;
        this.player = player;
    }

    private BufferStrategy setBufferStrategy() {
        bufferStrategy = main.getBufferStrategy();
        if(bufferStrategy == null) {
            main.createBufferStrategy(3);
        }
        return bufferStrategy;
    }

    private Graphics2D getGraphicsVar() {
        return (Graphics2D) setBufferStrategy().getDrawGraphics();
    }


    /**
     * GAME RENDER METHODS
     */
    public void renderPlayer() {
        player.render(getGraphicsVar());
    }

    public void renderAsteroids() {
        for(int i = 0; i < main.asteroids.getAsteroidList().size(); i++) {
            main.asteroids.getAsteroidList().get(i).render(getGraphicsVar());
        }
    }

}
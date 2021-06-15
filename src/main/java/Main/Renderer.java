package Main;

import Main.MainClass;
import Sky.Asteroids;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer {

    MainClass main;

    private BufferStrategy bufferStrategy;

    public Renderer(MainClass main) {
        this.main = main;
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
    public void renderAsteroids() {
        for(int i = 0; i < main.asteroids.getAsteroidList().size(); i++) {
            main.asteroids.getAsteroidList().get(i).render(getGraphicsVar());
        }
    }

}
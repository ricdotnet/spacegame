package Main;

import Main.MainClass;

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

    public void renderPlayer() {
        main.player.render(getGraphicsVar());
    }

    public void renderBullet() {
        main.bulletController.render(getGraphicsVar());
    }

}

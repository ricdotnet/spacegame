package Main;

import Bullet.*;
import Monster.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class MainClass extends Canvas implements Runnable {

    public static final long serialVersionUID = 1L;

    private Boolean RUNNING = false;
    private Thread thread;

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static final String TITLE = "JFrame and Canvas Testing";

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferStrategy bufferStrategy;
    private BufferedImage spriteSheet = null;

    private Player player;

    private Bullet bullet;
    private BulletController bulletController;

    private Monster monster;
    private MonsterController monsterController;

    private long lastSecond, lastFrame;
    private int ticks, frames;
    private double delta;
    private double RANDOM_X, RANDOM_Y; // ?????????

    public static void main(String[] args) {
        MainClass main = new MainClass();
        main.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        main.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        main.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        main.setBackground(Color.CYAN);

        JFrame window = new JFrame(TITLE);
        window.add(main);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        main.start();
    }

    public void init() {
        requestFocus();
        ImageLoader imageLoader = new ImageLoader();
        spriteSheet = imageLoader.loadImage("/sprites.png");

        addKeyListener(new KeyInput(this));

        player = new Player((getWidth() / 2) - 16, getHeight() - 64, this);

        bulletController = new BulletController(this);
        monsterController = new MonsterController(this);

        monsterController.addMonster(new Monster(setRandomX(), setRandomY(), this));
    }

    private synchronized void start() {
        if(RUNNING)
            return;

        RUNNING = true;
        thread = new Thread(this);
        thread.start();
    }
    private synchronized void stop() {
        if(!RUNNING)
            return;

        RUNNING = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
        System.exit(1);
    }

    /**
     * Main loop game.
     * Use as reference always.
     */
    @Override
    public void run() {
        init();
        lastSecond = Time.nsNow();
        lastFrame = Time.msNow();
        frames = 0;
        ticks = 0;
        delta = 0;

        while (RUNNING) {
            long now = Time.nsNow();
            delta += (now - lastSecond) / Time.NS_PER_TICK;
            lastSecond = now;
            if(delta >= 1) {
                tick();
                ticks++;
                delta--;
            }

            render();
            frames++;

            if(Time.msNow() - lastFrame > 1000) {
                lastFrame += 1000;
                System.out.println(ticks + " Ticks, FPS " + frames);
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        player.tick();
        bulletController.tick();
        monsterController.tick();

        monsterShot();
    }
    private void render() {

        bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
        // ---------- //

        graphics.setColor(Color.CYAN);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        player.render(graphics);
        bulletController.render(graphics);
        monsterController.render(graphics);

        // ---------- //
        graphics.dispose();
        bufferStrategy.show();
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    /*
    Key events
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(5);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setVelX(-5);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setVelY(-5);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setVelY(5);
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {

            //allow only 2 bullets on the screen at once
            if(bulletController.getBulletList().size() < 2) {
                bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, this));
            }

        }

        /**
         * TEMP KEY COMMANDS
         */
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            monsterController.addMonster(new Monster(setRandomX(), setRandomY(), this));
        }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setVelX(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setVelY(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setVelY(0);
        }
    }

    /**
     * Random X and random Y
     */
    private double setRandomX() {
        double x = Math.floor(Math.random() * getWidth());

        if(x >= getWidth() - 32) {
            x = x - 32;
        }
        return x;
    }
    private double setRandomY() {
        return Math.floor(Math.random() * (getHeight() / 2));
    }

    /**
     * TEMP CODES
     */
    public void monsterShot() {
        if(bulletController.getBulletList().size() > 0) {
            for (int i = 0; i < monsterController.getMonsterList().size(); i++) {
                for (int j = 0; j < bulletController.getBulletList().size(); j++) {
                    monster = monsterController.getMonsterList().get(i);
                    bullet = bulletController.getBulletList().get(j);

                    //kill monster and add another one
                    if((bullet.getxPOS() + 16 > monster.getxPOS() && bullet.getxPOS() + 16 < monster.getxPOS() + 32) && (bullet.getyPOS() > monster.getyPOS() && bullet.getyPOS() < monster.getyPOS() + 32)) {
                        monsterController.removeMonster(monster);
                        bulletController.removeBullet(bullet);

                        monsterController.addMonster(new Monster(setRandomX(), setRandomY(), this));
                    }
                }
            }
        }
    }

}

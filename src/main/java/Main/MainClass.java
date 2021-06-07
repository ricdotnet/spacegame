package Main;

import Bomb.*;
import Bullet.*;
import Database.Database;
import Monster.*;
import Player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainClass extends Canvas implements Runnable {

    public static final long serialVersionUID = 1L;

    private Boolean RUNNING = false;
    private Thread thread;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static final String TITLE = "Space Game";

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferStrategy bufferStrategy;
    private static BufferStrategy test;

    private BufferedImage spriteSheet = null;
    private BufferedImage icons = null;

    SoundLoader sound = new SoundLoader();

    private Player player;
    private PlayerVars playerVars = new PlayerVars();

    private Bullet bullet;
    private BulletController bulletController;

    private Monster monster;
    private MonsterController monsterController;
    public Explosion explosion = null;

    private Bomb bomb;
    private BombController bombController;

//    private Heart heart;
    private HeartController heartController;

    private long lastSecond, lastFrame;
    private int ticks, frames;
    private double delta;
    private double RANDOM_X, RANDOM_Y; // ?????????
    private int SPAWN_SIZE;

    Scores scores = new Scores();
    AskName askName = new AskName();
    GameVars gameVars = new GameVars();
    static Database connect = new Database();

    static JFrame window = new JFrame(TITLE);
    static MainClass main = new MainClass();
    static MainMenu mainMenu = new MainMenu();

    public MainClass() { }

    static JTextArea scoresList = new JTextArea();
    public static void main(String[] args) {

        if(connect.getConnection() != null) {
            System.out.println("Connected to the database.");
        }

        window.add(mainMenu);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        main.keys();
    }

    // main game methods
    public void startGame(ActionEvent e) {
        window.remove(mainMenu);
        window.add(main);
        SwingUtilities.updateComponentTreeUI(window);
        main.start();
    }
    public void quitGame(ActionEvent e) {
        System.exit(1);
    }

    /*
    Key starter.
    this avoids multiple key listeners on restart.
     */
    private void keys() {
        System.out.println("keys started");
        addKeyListener(new KeyInput(this));
    }
    /*
    Buttons starter method
     */
    private void buttons(JButton button) {
        button.addActionListener(new ButtonClick(this));
    }

    public void init() {
        requestFocus();
        ImageLoader imageLoader = new ImageLoader();
        spriteSheet = imageLoader.loadImage("/sprites.png");
        icons = imageLoader.loadImage("/icons.png");

        player = new Player((getWidth() / 2) - 16, getHeight() - 64, this);

        bulletController = new BulletController(this);
        monsterController = new MonsterController(this);
        bombController = new BombController(this);
        heartController = new HeartController(this);

        monsterController.addMonster(new Monster(setRandomX(), setRandomY(), this));

        // temp lives
        double x = 50;
        double y = getHeight() - 50;
        for(int i = 0; i < 5; i++) {
            heartController.addHeart(new Heart(x, y, this));
            x = x + 20;
        }
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
                addBomb();
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
        bombController.tick();
        heartController.tick();

        monsterKilled();
        userHit();
        monsterOut();

        gameFinished(); //check for when game end
    }
    private void render() {

        bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
        // ---------- //

        graphics.setColor(Colors.DEEP_BLUE);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        drawScore(graphics);

        player.render(graphics);
        bulletController.render(graphics);
        monsterController.render(graphics);
        bombController.render(graphics);
        heartController.render(graphics);

        if(explosion != null) {
            explosion.render(graphics);
        }

        // ---------- //
        graphics.dispose();
        bufferStrategy.show();
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.black);
        Font small = new Font("Monospace", Font.BOLD, 14);
        g.setFont(small);
        g.drawString("Current score: " + playerVars.getPlayerScore(), 50, getHeight()-15);
    }

    private void drawScoresTable() {

//        StringBuilder scoresList = new StringBuilder();
        scoresList.setText("");

        if(scores.printScores().size() > 0) {
            for (int i = 0; i < scores.printScores().size(); i++) {
                scoresList.append(String.valueOf(scores.printScores().get(i)) + "\n");
            }
        } else {
            scoresList.append("No scores...");
        }

//        g.drawString(String.valueOf(scoresList), 50, getHeight()-15);
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
    public BufferedImage getIconsSheet() {
        return icons;
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
            playerShoot();
        }

        /**
         * TEMP KEY COMMANDS
         */
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            monsterController.addMonster(new Monster(setRandomX(), setRandomY(), this));
            System.out.println(monsterController.getMonsterList().size());
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
    public void playerShoot() {

        switch (gameVars.getDifficulty()) {
            case ("Easy"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 10) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, this));
                    sound.playSound("/playerShoot.wav");
                }
                break;
            case ("Medium"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 5) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, this));
                    sound.playSound("/playerShoot.wav");
                }
                break;
            case ("Hard"):
                //allow only 2 bullets on the screen at once
                if(bulletController.getBulletList().size() < 2) {
                    bulletController.addBullet(new Bullet(player.getxPOS(), player.getyPOS() - 32, this));
                    sound.playSound("/playerShoot.wav");
                }
                break;
        }
    }

    public void monsterKilled() {
        if(bulletController.getBulletList().size() > 0) {
            for (int i = 0; i < monsterController.getMonsterList().size(); i++) {
                for (int j = 0; j < bulletController.getBulletList().size(); j++) {
                    monster = monsterController.getMonsterList().get(i);
                    bullet = bulletController.getBulletList().get(j);

                    //kill monster and add another one
                    if((bullet.getxPOS() + 16 > monster.getxPOS() && bullet.getxPOS() + 16 < monster.getxPOS() + 32) && (bullet.getyPOS() > monster.getyPOS() && bullet.getyPOS() < monster.getyPOS() + 32)) {

                        double tempX = monster.getxPOS();
                        double tempY = monster.getyPOS();

                        monsterController.removeMonster(monster);
                        bulletController.removeBullet(bullet);

                        sound.playSound("/enemyExplode.wav");

                        //spawn explosion image
                        explosion = new Explosion(tempX, tempY, this);

                        service.schedule(removeExplosion, 150, TimeUnit.MILLISECONDS);

                        if(Time.chance() < 0.01) {
                            SPAWN_SIZE = 6;
                        } else if(Time.chance() < 0.15) {
                            SPAWN_SIZE = 3;
                        } else if(Time.chance() < 0.30) {
                            SPAWN_SIZE = 2;
                        } else {
                            SPAWN_SIZE = 1;
                        }

                        for(int k = 0; k < SPAWN_SIZE; k++) {
                            RANDOM_X = setRandomX();
                            RANDOM_Y = setRandomY();

                            monsterController.addMonster(new Monster(RANDOM_X, RANDOM_Y, this));
                        }

                        //increase score by 1
                        playerVars.setPlayerScore();
                    }
                }
            }
        }
    }

    public void monsterOut() {
        for(int i = 0; i < monsterController.getMonsterList().size(); i++) {
            monster = monsterController.getMonsterList().get(i);
            if(monster.getyPOS() > getHeight()) {
                monsterController.removeMonster(monster);
                heartController.removeHeart();
            }
        }
    }

    public void userHit() {
        for(int i = 0; i < bombController.getBombList().size(); i++) {
            bomb = bombController.getBombList().get(i);

            if(((bomb.getyPOS() > player.getyPOS() - 32) && bomb.getyPOS() < player.getyPOS()) && (bomb.getxPOS() + 32 > player.getxPOS() && bomb.getxPOS() < player.getxPOS() + 32)) {
                bombController.removeBomb(bomb);
                heartController.removeHeart();
            }
        }
    }

    public void addBomb() {
        Monster randomMonster = null;

        if(monsterController.getMonsterList().size() > 1) {
            randomMonster = monsterController.getMonsterList().get((int) Math.ceil(Math.random() * (monsterController.getMonsterList().size() - 1)));
        } else if(monsterController.getMonsterList().size() == 1) {
            randomMonster = monsterController.getMonsterList().get(0);
        }

        if(bombController.getBombList().size() == 0 && randomMonster != null) {
            bombController.addBomb(new Bomb(randomMonster.getxPOS(), randomMonster.getyPOS() + 32, this));

            sound.playSound("/enemyShoot.wav");
        }
    }

    public void gameFinished() {
        if(heartController.getHeartList().size() == 0) {

            //explosion = new Explosion(player.getxPOS(), player.getyPOS(), this);

            sound.playSound("/gameOver.wav");
            RUNNING = false;
            service.schedule(restart, 2, TimeUnit.SECONDS);
        }
    }

    // main game runnable methods
    Runnable removeExplosion = new Runnable() {
        @Override
        public void run() {
            explosion = null;
        }
    };

    Runnable restart = new Runnable() {
        @Override
        public void run() {
            window.remove(main);

            askName.setVisible(true);

            askName.confirmButton.addActionListener(new ButtonClick(main));
        }
    };

    public void restart() {
        if(!askName.nameField.getText().isEmpty()) {
            playerVars.setPlayerName(askName.nameField.getText());
            askName.nameField.setBackground(Color.white);
            askName.nameField.setText("");
            askName.setVisible(false);
            scores.addScore(playerVars.getPlayerScore());

            playerVars.resetPlayerScore();

            drawScoresTable();
            window.add(mainMenu);
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

}

package Main;

import Bomb.*;
import Bullet.*;
import Database.Database;
import Monster.*;
import Player.*;
import Util.Colors;
import Util.Time;
import Util.Util;

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

    private Boolean PAUSED = false; //state fo the game; false for not playing <-> true for playing
    private Boolean RUNNING = false;
    private Thread thread;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static final String TITLE = "Space Game";

//    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferStrategy bufferStrategy;

    private BufferedImage spriteSheet = null;
    private BufferedImage icons = null;

    SoundLoader sound = new SoundLoader();

    private Player player;
    private PlayerVars playerVars = new PlayerVars();

    private Bullet bullet;
    private BulletController bulletController;

    private Monster monster;
    private MonsterController monsterController;
    private MonsterEvents monsterEvents = new MonsterEvents(this);
    public Explosion explosion = null;

    private Bomb bomb;
    private BombController bombController;

//    private Heart heart;
    private HeartController heartController;

    private ExplosionController explosionController;

    private long lastSecond, lastFrame;
    private int ticks, frames;
    private double delta;
    private double RANDOM_X, RANDOM_Y; // ?????????

    Scores scores = new Scores();
    AskName askName = new AskName();
    GameVars gameVars = new GameVars();
    Util util = new Util();
    static Database connect = new Database();

    static JFrame window = new JFrame(TITLE);
    static MainClass main = new MainClass();
    static MainMenu mainMenu = new MainMenu();
    static PausedScreen pausedScreen = new PausedScreen();

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

//        scores.getScores();
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
        explosionController = new ExplosionController(this);

        monsterController.addMonster(new Monster(util.setRandomX(), util.setRandomY(), this));

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

        sound.playBackgroundMusic("/backgroundMusic.wav");

        while (RUNNING) {
            long now = Time.nsNow();
            delta += (now - lastSecond) / Time.NS_PER_TICK;
            lastSecond = now;
            if (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }

            render();
            frames++;

            if (Time.msNow() - lastFrame > 1000) {
                lastFrame += 1000;
                System.out.println(ticks + " Ticks, FPS " + frames);
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        if(!PAUSED) {
            player.tick();
            bulletController.tick();
            monsterController.tick();
            bombController.tick();
            heartController.tick();
            explosionController.tick();

            monsterEvents.monsterKilled();
            userHit();
            monsterOut();

            //TODO change the tick for bomb drop
            if(util.chance() < 0.1) {
                addBomb();
            }

            gameFinished(); //check for when game end
        }
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
        explosionController.render(graphics);

//        if(explosion != null) {
//            explosion.render(graphics);
//        }

        if(PAUSED) {
            pausedScreen.pausedScreen(graphics);
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

//    private void drawScoresTable(Graphics g) {
//
////        StringBuilder scoresList = new StringBuilder();
//        scoresList.setText("");
//
//        if(scores.printScores().size() > 0) {
//            for (int i = 0; i < scores.printScores().size(); i++) {
//                scoresList.append(String.valueOf(scores.printScores().get(i)) + "\n");
//            }
//        } else {
//            scoresList.append("No scores...");
//        }
//
//        g.drawString(String.valueOf(scoresList), 50, getHeight()-15);
//    }

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

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pauseUnpause();
        }

        if(e.getKeyCode() == KeyEvent.VK_R) {
            if(PAUSED) {
                window.remove(main);
                window.add(mainMenu);
                RUNNING = false;
                PAUSED = false;
                playerVars.resetPlayerScore();
                sound.stopBackgroundMusic();
            }
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

            if(((bomb.getyPOS() > player.getyPOS() - 15) && bomb.getyPOS() < player.getyPOS()) && (bomb.getxPOS() + 20 > player.getxPOS() && bomb.getxPOS() + 10 < player.getxPOS() + 32)) {
                bombController.removeBomb(bomb);
                heartController.removeHeart();
            }
        }
    }

    public void addBomb() {

        Monster randomMonster = monsterController.getMonsterList().get(0);

        if(monsterController.getMonsterList().size() > 1) {
            for(int i = 0; i < monsterController.getMonsterList().size(); i++) {

                //if the next monster position is closer to the player then assign a new monster
                if(Math.abs(randomMonster.getxPOS() - player.getxPOS()) > Math.abs(monsterController.getMonsterList().get(i+1).getxPOS() - player.getxPOS())) {
                    randomMonster = monsterController.getMonsterList().get(i+1);
                    // System.out.println("found a closer monster"); //message to say a closer monster has been found
                }

                //break the loop when there are no more monsters to count with
                if(i+1 == monsterController.getMonsterList().size() - 1) {
                    break;
                }

            }
        }

        /**
         * Let the monster shoot only if there are no bombs in the screen
         * Will change this with the difficulty level
         */
        if(bombController.getBombList().size() == 0 && randomMonster != null && !gameVars.getIsTesting()) {
            bombController.addBomb(new Bomb(randomMonster.getxPOS(), randomMonster.getyPOS() + 32, this));
            sound.playSound("/enemyShoot.wav");
        }
    }

    public void gameFinished() {
        if(heartController.getHeartList().size() == 0) {

            //explosion = new Explosion(player.getxPOS(), player.getyPOS(), this);

            sound.stopBackgroundMusic();
            sound.playSound("/gameOver.wav");
            RUNNING = false;
            service.schedule(restart, 2, TimeUnit.SECONDS);
        }
    }

    Runnable restart = new Runnable() {
        @Override
        public void run() {
            window.remove(main);

            if(!gameVars.getIsTesting()) {
                askName.setVisible(true);
                askName.confirmButton.addActionListener(new ButtonClick(main));
            } else {
                playerVars.resetPlayerScore();
//                drawScoresTable();
                window.add(mainMenu);
                SwingUtilities.updateComponentTreeUI(window);
            }
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

//            drawScoresTable();
            window.add(mainMenu);
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public void pauseUnpause() {
        if(PAUSED) {
            sound.playSound("/unpauseSound.wav");
            PAUSED = false;
            sound.unpauseBackGroundMusic();
        } else {
            sound.playSound("/pauseSound.wav");
            PAUSED = true;
            sound.pauseBackgroundMusic();
        }
    }
}
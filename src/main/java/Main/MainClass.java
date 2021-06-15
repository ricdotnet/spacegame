package Main;

import Bomb.*;
import Bullet.*;
import Database.Database;
import Monster.*;
import Player.*;
import Sky.*;
import Util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public static int WIDTH = 640;
    public static int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 1;
    public static final String TITLE = "Space Game";

//    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferStrategy bufferStrategy;

    private BufferedImage spriteSheet = null;
    private BufferedImage icons = null;

    SoundLoader sound = new SoundLoader();

    private Player player;
    private PlayerEvents playerEvents;
    private PlayerVars playerVars = new PlayerVars();

    private BulletController bulletController;

    private MonsterController monsterController;
    private MonsterEvents monsterEvents;

    private Bomb bomb;
    private BombController bombController;

    private HeartController heartController;

    private ExplosionController explosionController;

    private Stars stars;

    public Asteroids asteroids;

    private long lastSecond, lastFrame;
    private int ticks, frames;
    private double delta;

    Scores scores = new Scores();
    AskName askName = new AskName();
    GameVars gameVars = new GameVars();
    Util util = new Util();
    Time time = new Time();
    static Database connect = new Database();

    static JFrame window = new JFrame(TITLE);
    static MainClass main = new MainClass();
    static MainMenu mainMenu = new MainMenu();
    static PausedScreen pausedScreen = new PausedScreen();

    Renderer renderer;

    KeyInput keyInput;
    MouseInput mouseInput;

    public MainClass() { }

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
        main.mouse();
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
        if(keyInput != null) {
            System.out.println("Key listener already started;");
            return;
        }

        System.out.println("Key listener started.");
        keyInput = new KeyInput();
        addKeyListener(keyInput);
    }
    private void mouse() {
        if(mouseInput != null) {
            System.out.println("Mouse listener already started.");
            return;
        }

        System.out.println("Mouse listener started.");
        mouseInput = new MouseInput();
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    /*
    Buttons starter method example
     */
    private void buttons(JButton button) {
        button.addActionListener(new ButtonClick());
    }

    public void init() {
        timer = time.msNow();
        requestFocus();
        ImageLoader imageLoader = new ImageLoader();
        spriteSheet = imageLoader.loadImage("/sprites.png");
        icons = imageLoader.loadImage("/icons.png");

        player = new Player((getWidth() / 2) - 16, getHeight() - 64, this);
        monsterEvents = new MonsterEvents(this, player);
        playerEvents = new PlayerEvents(this, player);

        bulletController = new BulletController(this);
        monsterController = new MonsterController(this);
        bombController = new BombController(this);
        heartController = new HeartController(this);
        explosionController = new ExplosionController(this);
        asteroids = new Asteroids(this);

        renderer = new Renderer(this, player);

        double monsterYpos = util.setRandomY();
        if(monsterYpos > HEIGHT/2) {
            monsterYpos -= 250;
        }
        monsterController.addMonster(new Monster(util.setRandomX(), monsterYpos, this));

        // stars need to generate and render always before anything else
        stars = new Stars();
        stars.generateStars(); //generate new stars

        // temp lives
        double x = 50;
        double y = getHeight() - 50;
        for(int i = 0; i < 5; i++) {
            heartController.addHeart(new Heart(x, y, this));
            x = x + 20;
        }

        // initialize the key listener
        keyInput.setPlayer(player);
        keyInput.setPlayerEvents(playerEvents);
        mouseInput.setPlayer(player);
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
        lastSecond = time.nsNow();
        lastFrame = time.msNow();
        frames = 0;
        ticks = 0;
        delta = 0;

        sound.playBackgroundMusic("/backgroundMusic.wav");

        while (RUNNING) {
            long now = time.nsNow();
            delta += (now - lastSecond) / Time.NS_PER_TICK;
            lastSecond = now;
            if (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }

            render();
            frames++;

            if (time.msNow() - lastFrame > 1000) {
                lastFrame += 1000;
                System.out.println(ticks + " Ticks, FPS " + frames);
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
//        timer();
        if(!PAUSED) {
            gameFinished(); //check for when game end

            player.tick();
            bulletController.tick();
            monsterController.tick();
            bombController.tick();
            heartController.tick();
            explosionController.tick();
            asteroids.tick();

            monsterEvents.monsterKilled();
            monsterEvents.monsterOut();
            playerEvents.userHit();
            playerEvents.asteroidCollision();

            stars.tick(); //move stars down
            randomEvent();

            //TODO change the tick for bomb drop
            if(util.chance() < 0.1) {
                monsterEvents.addBomb();
            }
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
        heartController.render(graphics);
        stars.render(graphics);

//        player.render(graphics);
        renderer.renderPlayer();

        bulletController.render(graphics);
        monsterController.render(graphics);
        bombController.render(graphics);
        explosionController.render(graphics);

        renderer.renderAsteroids();

        if(PAUSED) {
            pausedScreen.pausedScreen(graphics);
        }

        // ---------- //
        graphics.dispose();
        bufferStrategy.show();
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
    public BufferedImage getIconsSheet() {
        return icons;
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.black);
        Font small = new Font("Monospace", Font.BOLD, 14);
        g.setFont(small);
        g.drawString("Current score: " + playerVars.getPlayerScore(), 50, HEIGHT-15);
    }

    public void gameFinished() {
        if(heartController.getHeartList().size() == 0 || monsterController.getMonsterList().size() == 0) {

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
                askName.confirmButton.addActionListener(new ButtonClick());
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
            resetGameLists();
            player = null;

//            drawScoresTable();
            window.add(mainMenu);
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public void resetOnPause() {
        if(PAUSED) {
            window.remove(main);
            window.add(mainMenu);
            RUNNING = false;
            PAUSED = false;
            playerVars.resetPlayerScore();
            sound.stopBackgroundMusic();
            resetGameLists();

            player = null;
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

    public void resetGameLists() {
        bulletController.getBulletList().clear();
        explosionController.getExplosionsList().clear();
        bombController.getBombList().clear();
        monsterController.getMonsterList().clear();
    }

    public void setWindowSize() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
    }

    /**
     * TEMP CODE
     */
    public void randomEvent() {
        if(util.chance() < 0.01 && asteroids.getAsteroidList().size() < 3) {
            asteroids.getAsteroidList().add(new Asteroid(util.setRandomX(), -32, (int) Math.ceil(util.chance() * 5), main));
        }
    }

    private long timer;
    private int counter = 0;
    public void timer() {
        if(time.msNow() - timer >= 1000) {
            System.out.println(counter++);
            timer = time.msNow();
        }
    }
}
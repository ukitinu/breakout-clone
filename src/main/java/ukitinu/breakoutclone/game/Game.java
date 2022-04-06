package ukitinu.breakoutclone.game;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Menu;
import ukitinu.breakoutclone.*;

import java.awt.*;

import static ukitinu.breakoutclone.game.GameConst.TARGET_FPS_HIGH;
import static ukitinu.breakoutclone.game.GameConst.TARGET_FPS_LOW;

public class Game extends Canvas implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Game.class);

    private int millisCounter = 0;
    private static final int MILLIS_COUNTER_MAX = 10;
    private int millisWait = 10;

    private long timer;
    private int fps;

    private boolean running = false;
    private Thread thread;
    private final Window window;

    @Getter(AccessLevel.PACKAGE)
    private int level;
    @Getter(AccessLevel.PACKAGE)
    private GameState state;

    Game() {
        addKeyListener(new KeyListener());
        addKeyListener(ukitinu.breakoutclone.Menu.INSTANCE);

        this.window = new Window(GameConst.WIDTH, GameConst.HEIGHT, this);
        this.level = 1;

        initLevel();
    }

    void initLevel() {
        window.setLevel(level);
        Spawner.INSTANCE.placeBricks(2);
        Spawner.INSTANCE.placeBall();
        Spawner.INSTANCE.placePaddle();

        this.state = GameState.PLAY;
        tick();
        this.state = GameState.PAUSE;
    }

    void start() {
        synchronized (this) {
            thread = new Thread(this);
            thread.start();
            running = true;
            this.requestFocus();
        }
    }

    private void stop() {
        synchronized (this) {
            try {
                thread.join();
                running = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalThreadStateException("Game::stop interrupted");
            }
        }
    }

    void switchState() {
        synchronized (this) {
            if (state == GameState.PAUSE) {
                state = GameState.PLAY;
            } else if (state == GameState.PLAY) {
                state = GameState.PAUSE;
            }
        }
    }

    @Override
    public void run() {
        long prev = System.nanoTime();
        double skippedFrames = 0;
        timer = System.currentTimeMillis();
        fps = 0;
        while (running) {
            long now = System.nanoTime();
            skippedFrames += (now - prev) / ((double) GameConst.OPTIMAL_TIME);
            prev = now;

            while (skippedFrames > 0) {
                tick();
                skippedFrames--;
            }

            if (running) render();

            fps++;

            if (System.currentTimeMillis() - timer > 1_000) controlFps();

            try {
                //noinspection BusyWait
                Thread.sleep(millisWait);
            } catch (InterruptedException e) {
                LOG.error("Game::run interrupted");
                running = false;
                break;
            }
        }
        stop();
    }

    private void controlFps() {
        timer += 1000;
        LOG.info("FPS: {}, millisWait: {}", fps, millisWait);

        if (fps < TARGET_FPS_LOW) {
            if (millisCounter <= -MILLIS_COUNTER_MAX) {
                millisWait--;
                millisCounter = 0;
            } else {
                millisCounter--;
            }
        } else if (fps > TARGET_FPS_HIGH) {
            if (millisCounter >= MILLIS_COUNTER_MAX) {
                millisWait++;
                millisCounter = 0;
            } else {
                millisCounter++;
            }
        } else {
            millisCounter = 0;
        }
        fps = 0;
    }

    private void tick() {
        if (state == GameState.PLAY) {
            Room.INSTANCE.tick();
            HUD.INSTANCE.tick();
        } else if (state == GameState.PAUSE) {
            Menu.INSTANCE.tick();
        }
        if (Room.INSTANCE.countBricks() == 0) {
            Room.INSTANCE.clear();
            Room.INSTANCE.tick();
            level++;
            switchState();
            initLevel();
        }
    }

    private void render() {
        var bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, HUD.HEIGHT, GameConst.WIDTH, GameConst.HEIGHT - HUD.HEIGHT);

        Room.INSTANCE.render(g);
        HUD.INSTANCE.render(g);

        if (state == GameState.PAUSE) {
            Menu.INSTANCE.render(g);
        }

        g.dispose();
        bs.show();
    }
}

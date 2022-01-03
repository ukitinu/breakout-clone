package org.ukitinu.breakoutclone;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Game extends Canvas implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Game.class);

    private boolean running = false;
    private Thread thread;
    private final Window window;

    @Getter(AccessLevel.PACKAGE)
    private int level;
    @Getter(AccessLevel.PACKAGE)
    private GameState state;

    Game() {
        addKeyListener(new KeyListener());
        addKeyListener(Menu.INSTANCE);

        this.window = new Window(GameConst.WIDTH, GameConst.HEIGHT, this);
        this.level = 1;

        initLevel();
    }

    void initLevel() {
        window.setLevel(level);
        Spawner.INSTANCE.placeBricks(1);
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
        long timer = System.currentTimeMillis();
        int frameCount = 0;
        while (running) {
            long now = System.nanoTime();
            skippedFrames += (now - prev) / ((double) GameConst.OPTIMAL_TIME);
            prev = now;

            while (skippedFrames > 0) {
                tick();
                skippedFrames--;
            }

            if (running) render();

            frameCount++;

            if (System.currentTimeMillis() - timer > 1_000) {
                timer += 1000;
                LOG.info("FPS: {}", frameCount);
                frameCount = 0;
            }
            try {
                //noinspection BusyWait
                Thread.sleep(5);
            } catch (InterruptedException e) {
                LOG.error("Game::run interrupted");
                running = false;
                break;
            }
        }
        stop();
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

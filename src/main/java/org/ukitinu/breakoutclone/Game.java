package org.ukitinu.breakoutclone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;

    private static final int TARGET_FPS = 60;
    private static final long NANOS_SEC = 1000_000_000;
    private static final long OPTIMAL_TIME = NANOS_SEC / TARGET_FPS;

    private static final Logger LOG = LogManager.getLogger(Game.class);

    private boolean running = false;
    private final Room room = Room.INSTANCE;

    private Thread thread;

    public Game(String title) {
        this.addKeyListener(new KeyListener());

        new Window(WIDTH, HEIGHT, title, this);

        Spawner.INSTANCE.placeBricks(6);
        Spawner.INSTANCE.placeBall();
        Spawner.INSTANCE.placePaddle();
    }

    public void start() {
        synchronized (this) {
            thread = new Thread(this);
            thread.start();
            running = true;
            this.requestFocus();
        }
    }

    public void stop() {
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

    @Override
    public void run() {
        long prev = System.nanoTime();
        double skippedFrames = 0;
        long timer = System.currentTimeMillis();
        int frameCount = 0;
        while (running) {
            long now = System.nanoTime();
            skippedFrames += (now - prev) / ((double) OPTIMAL_TIME);
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
        room.tick();
        HUD.INSTANCE.tick();
    }

    private void render() {
        var bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, HUD.HEIGHT, WIDTH, HEIGHT - HUD.HEIGHT);

        room.render(g);
        HUD.INSTANCE.render(g);

        g.dispose();
        bs.show();
    }
}

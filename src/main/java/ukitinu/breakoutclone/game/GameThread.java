package ukitinu.breakoutclone.game;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Conf;
import ukitinu.breakoutclone.KeyListener;
import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.gui.HUD;
import ukitinu.breakoutclone.gui.Menu;
import ukitinu.breakoutclone.gui.Window;

import java.awt.*;

import static ukitinu.breakoutclone.game.GameConst.TARGET_FPS_HIGH;
import static ukitinu.breakoutclone.game.GameConst.TARGET_FPS_LOW;

class GameThread extends Canvas implements Runnable {
    private static final Logger LOG = LogManager.getLogger(GameThread.class);

    private int millisCounter = 0;
    private static final int MILLIS_COUNTER_MAX = 10;
    private int millisWait = 10;

    private long timer;
    private int fps;

    private boolean running = false;
    private Thread thread;
    private final Window window;

    @Getter(AccessLevel.PACKAGE)
    private GameState state;

    GameThread() {
        if (Conf.LOG_THREAD.bool()) LOG.info("Adding key listener: {}", KeyListener.class.getSimpleName());
        addKeyListener(new KeyListener());
        if (Conf.LOG_THREAD.bool()) LOG.info("Adding key listener: {}", Menu.class.getSimpleName());
        addKeyListener(Menu.INSTANCE);

        if (Conf.LOG_THREAD.bool()) LOG.info("Creating game window");
        this.window = new Window(GameConst.WIDTH, GameConst.HEIGHT, this);
    }

    void start() {
        if (Conf.LOG_THREAD.bool()) LOG.info("Executing first tick");
        this.state = GameState.PLAY;
        tick();
        this.state = GameState.PAUSE;
        window.setTitle();

        if (Conf.LOG_THREAD.bool()) LOG.info("Starting game thread");
        thread = new Thread(this);
        thread.start();
        running = true;
        this.requestFocus();
    }

    private void stop() {
        try {
            if (Conf.LOG_THREAD.bool()) LOG.info("Stopping game thread");
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalThreadStateException("GameThread::stop interrupted");
        }
    }

    void switchState() {
        if (Conf.LOG_THREAD.bool()) LOG.info("Switching game state from {}", state);
        if (state == GameState.PAUSE) {
            state = GameState.PLAY;
        } else if (state == GameState.PLAY) {
            state = GameState.PAUSE;
        }
    }

    void setState(GameState state) {
        this.state = state;
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
                LOG.error("GameThread::run interrupted");
                running = false;
                break;
            }
        }
        stop();
    }

    private void controlFps() {
        timer += 1000;
        if (Conf.LOG_FPS.bool()) LOG.info("FPS: {}", fps);

        if (fps < TARGET_FPS_LOW) {
            if (millisCounter <= -MILLIS_COUNTER_MAX) {
                millisWait--;
                if (Conf.LOG_THREAD.bool()) LOG.info("Decreasing millisWait to {}", millisWait);
                millisCounter = 0;
            } else {
                millisCounter--;
            }
        } else if (fps > TARGET_FPS_HIGH) {
            if (millisCounter >= MILLIS_COUNTER_MAX) {
                millisWait++;
                if (Conf.LOG_THREAD.bool()) LOG.info("Increasing millisWait to {}", millisWait);
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
        } else if (state == GameState.PAUSE && !Game.hasWon()) {
            Menu.INSTANCE.tick();
        } else if (state == GameState.PAUSE) {
            Menu.INSTANCE.tick();
            Room.INSTANCE.tick();
        }
        if (Room.INSTANCE.countBricks() <= 0 && !Game.hasWon()) {
            Game.nextLevel();
            window.setTitle();
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

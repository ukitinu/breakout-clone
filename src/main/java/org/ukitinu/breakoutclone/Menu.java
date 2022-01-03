package org.ukitinu.breakoutclone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public enum Menu implements Drawable, KeyListener {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Menu.class);

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Font font = new Font(Font.MONOSPACED, Font.BOLD, MAIN_SIZE);
        g.setFont(font);

        g.setColor(Color.BLUE);
        g.drawString(MAIN_MESSAGE, MAIN_X, Game.HEIGHT / 2);

        font = new Font(Font.MONOSPACED, Font.PLAIN, SECOND_SIZE);
        g.setFont(font);
        g.drawString(RESUME_MESSAGE, SECOND_X, RESUME_Y);
        g.drawString(QUIT_MESSAGE, SECOND_X, QUIT_Y);
        g.drawString(KEYS_MESSAGE, KEYS_X, KEYS_Y);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //unused
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getExtendedKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            LOG.info("ESC");
            System.exit(0);

        } else if (key == KeyEvent.VK_SPACE) {
            LOG.info("SPACEBAR");
            Main.GAME.switchState();
        }
    }

    private static final String MAIN_MESSAGE = "PAUSED";
    private static final int MAIN_SIZE = 20;
    private static final int MAIN_X = Game.WIDTH / 2 - MAIN_SIZE * MAIN_MESSAGE.length() / 2;

    private static final int SECOND_SIZE = 16;
    private static final int SECOND_X = Game.WIDTH / 2 - MAIN_SIZE * MAIN_MESSAGE.length();

    private static final String RESUME_MESSAGE = "[SPACE] - (UN)PAUSE";
    private static final int RESUME_Y = Game.HEIGHT / 2 + 48;

    private static final String QUIT_MESSAGE = "[ESC]   - QUIT";
    private static final int QUIT_Y = RESUME_Y + SECOND_SIZE + 8;

    private static final String KEYS_MESSAGE = "Use left and right arrows to move";
    private static final int KEYS_Y = QUIT_Y + SECOND_SIZE + 16;
    private static final int KEYS_X = Game.WIDTH / 2 - SECOND_SIZE * KEYS_MESSAGE.length() / 3;
}

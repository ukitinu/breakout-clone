package ukitinu.breakoutclone.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Drawable;
import ukitinu.breakoutclone.game.Game;
import ukitinu.breakoutclone.game.GameConst;
import ukitinu.breakoutclone.game.GameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public enum Menu implements Drawable, KeyListener {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Menu.class);

    @Override
    public void tick() {
        // nothing to update tick by tick
    }

    @Override
    public void render(Graphics g) {
        Font font = new Font(Font.MONOSPACED, Font.BOLD, MAIN_SIZE);
        g.setFont(font);

        if (Game.lifeLost) {
            g.setColor(Color.CYAN);
            g.drawString(Game.lives < GameConst.MAX_LIVES ? "Life lost!" : "Game lost!",
                    TEXT_X, GameConst.HEIGHT / 2 - MAIN_SIZE * 3);
            g.drawString(Game.lives < GameConst.MAX_LIVES ? "Level restarted and score reset" : "Full reset",
                    TEXT_X, GameConst.HEIGHT / 2 - MAIN_SIZE * 2);
        }

        g.setColor(Game.level >= GameConst.MAX_LEVEL ? Color.MAGENTA : Color.BLUE);
        String title = Game.level >= GameConst.MAX_LEVEL ? "Last level!" : "Level " + Game.level;
        g.drawString(title, TEXT_X, GameConst.HEIGHT / 2);

        font = new Font(Font.MONOSPACED, Font.PLAIN, SECOND_SIZE);
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.drawString(RESUME_MESSAGE, TEXT_X, RESUME_Y);
        g.drawString(QUIT_MESSAGE, TEXT_X, QUIT_Y);
        g.drawString(KEYS_MESSAGE, TEXT_X, KEYS_Y);
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
            Game.lifeLost = false;
            GameManager.INSTANCE.switchGameState();
        }
    }


    private static final int TEXT_X = GameConst.WIDTH / 4;
    private static final int MAIN_SIZE = 20;

    private static final int SECOND_SIZE = 16;

    private static final String RESUME_MESSAGE = "[SPACE] - (UN)PAUSE";
    private static final int RESUME_Y = GameConst.HEIGHT / 2 + 48;

    private static final String QUIT_MESSAGE = "[ESC]   - QUIT";
    private static final int QUIT_Y = RESUME_Y + SECOND_SIZE + 8;

    private static final String KEYS_MESSAGE = "Use left and right arrows to move";
    private static final int KEYS_Y = QUIT_Y + SECOND_SIZE + 16;
}

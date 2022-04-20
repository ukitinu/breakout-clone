package ukitinu.breakoutclone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private static final Logger LOG = LogManager.getLogger(KeyListener.class);

    private final Room room = Room.INSTANCE;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getExtendedKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            room.getPlayerPaddle().onKeyPressed(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getExtendedKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            if (Conf.LOG_KEYS.bool()) LOG.info("[{}] key released", key == KeyEvent.VK_LEFT ? "LEFT" : "RIGHT");
            room.getPlayerPaddle().onKeyReleased(key);
        }
    }
}

package org.ukitinu.breakoutclone;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

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
            room.getPlayerPaddle().onKeyReleased(key);
        }
    }
}

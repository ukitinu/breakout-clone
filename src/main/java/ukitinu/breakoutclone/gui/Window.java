package ukitinu.breakoutclone.gui;

import ukitinu.breakoutclone.game.Game;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {
    private static final String TITLE = "Yet another Breakout clone";

    private final JFrame frame = new JFrame();

    public Window(int width, int height, Component comp) {
        setTitle();

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(comp);
    }

    public void setTitle() {
        frame.setTitle(String.format("%s - Level %d", TITLE, Game.level));
    }

}

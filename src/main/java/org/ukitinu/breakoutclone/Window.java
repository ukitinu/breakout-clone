package org.ukitinu.breakoutclone;

import javax.swing.*;
import java.awt.*;

class Window extends Canvas {
    private static final String TITLE = "Yet another Breakout clone";
    private static final String TITLE_LEVEL = "%s - Level %d";

    private final JFrame frame = new JFrame();

    Window(int width, int height, Component comp) {
        setLevel(1);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(comp);
    }

    void setLevel(int level) {
        frame.setTitle(String.format(TITLE_LEVEL, TITLE, level));
    }

}

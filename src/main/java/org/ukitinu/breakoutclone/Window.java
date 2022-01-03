package org.ukitinu.breakoutclone;

import javax.swing.*;
import java.awt.*;

class Window extends Canvas {

    Window(int width, int height, String title, Component comp) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(comp);
    }

}

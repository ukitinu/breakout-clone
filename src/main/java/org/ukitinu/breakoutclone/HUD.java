package org.ukitinu.breakoutclone;

import org.ukitinu.breakoutclone.objects.HasScore;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public enum HUD implements Drawable {

    INSTANCE;

    private static final int HUD_HEIGHT = 16;
    private static final int HUD_WIDTH = 16;
    public static final int HEIGHT = HUD_HEIGHT + 2;

    private static final int MAX_LIVES = 3;
    private static final int ADJUSTMENT = 15;
    private static final int SCORE_Y = 16;

    private static int score = 0;
    private static int lives = MAX_LIVES;

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        drawContainer(g);
        drawLives(g);
        drawScore(g);
    }

    private void drawContainer(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, Game.WIDTH, HEIGHT);
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void drawLives(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(Game.WIDTH - HUD_WIDTH * MAX_LIVES - ADJUSTMENT, 0, HUD_WIDTH * MAX_LIVES, HUD_HEIGHT);

        g.setColor(Color.RED);
        g.fillRect(Game.WIDTH - HUD_WIDTH * lives - ADJUSTMENT, 0, HUD_WIDTH * lives, HUD_HEIGHT);

        g.setColor(Color.WHITE);
        for (int i = 1; i <= MAX_LIVES; i++) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(Game.WIDTH - HUD_WIDTH * i - ADJUSTMENT, 0, HUD_WIDTH, HUD_HEIGHT);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 0, SCORE_Y);
    }

    public void updateScore(HasScore obj) {
        score += obj.getScore();
    }

    public void dropLife() {
        lives--;
    }
}

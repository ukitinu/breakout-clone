package ukitinu.breakoutclone.gui;

import ukitinu.breakoutclone.Drawable;
import ukitinu.breakoutclone.game.Game;
import ukitinu.breakoutclone.game.GameConst;
import ukitinu.breakoutclone.objects.HasScore;

import java.awt.*;

public enum HUD implements Drawable {
    INSTANCE;

    private static final int HUD_HEIGHT = 16;
    private static final int HUD_WIDTH = 16;
    public static final int HEIGHT = HUD_HEIGHT + 2;

    private static final int ADJUSTMENT = 15;
    private static final int SCORE_Y = 16;

    @Override
    public void tick() {
        // nothing to update tick by tick
    }

    @Override
    public void render(Graphics g) {
        drawContainer(g);
        drawLives(g);
        drawScore(g);
    }

    private void drawContainer(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, GameConst.WIDTH, HEIGHT);
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void drawLives(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(
                GameConst.WIDTH - HUD_WIDTH * GameConst.MAX_LIVES - ADJUSTMENT,
                0,
                HUD_WIDTH * GameConst.MAX_LIVES,
                HUD_HEIGHT);

        g.setColor(Color.RED);
        g.fillRect(
                GameConst.WIDTH - HUD_WIDTH * Game.lives - ADJUSTMENT,
                0,
                HUD_WIDTH * Game.lives,
                HUD_HEIGHT);

        g.setColor(Color.WHITE);
        for (int i = 1; i <= GameConst.MAX_LIVES; i++) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(GameConst.WIDTH - HUD_WIDTH * i - ADJUSTMENT, 0, HUD_WIDTH, HUD_HEIGHT);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("SCORE: " + Game.score, 0, SCORE_Y);
    }

    public void updateScore(HasScore obj) {
        Game.score += obj.getScore();
    }
}

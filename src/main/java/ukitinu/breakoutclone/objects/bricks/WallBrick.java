package ukitinu.breakoutclone.objects.bricks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Conf;

import java.awt.*;

public class WallBrick extends AbstractBrick {
    private static final Logger LOG = LogManager.getLogger(WallBrick.class);

    private static final int BRICK_R = 155;
    private static final int BRICK_G = 103;
    private static final int BRICK_B = 31;

    private static final float FADE_OUT = 0.35f;

    private float alpha = 1;

    WallBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public void onHit() {
        if (Conf.LOG_GAME.bool()) LOG.info("WALL{} fading out", this.toString());
        if (alpha > 0) alpha = Math.max(0, alpha - FADE_OUT);
        else super.onHit();
    }

    @Override
    public void drawSymbol(Graphics g) {
        if (alpha > 0) drawBricks(g);
    }

    private void drawBricks(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(new Color(BRICK_R, BRICK_G, BRICK_B));
        g.fillRect(getXCentre() - 6 * getXUnit(), getYCentre() - 4 * getYUnit(), 12 * getXUnit(), 8 * getYUnit());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.WHITE);
        g2d.drawLine(getXCentre() - 6 * getXUnit(), getYCentre(), getXCentre() + 6 * getXUnit(), getYCentre());
        g2d.drawLine(getXCentre(), getYCentre() - 4 * getYUnit(), getXCentre(), getYCentre());
        g2d.drawLine(getXCentre() - 3 * getXUnit(), getYCentre(), getXCentre() - 3 * getXUnit(), getYCentre() + 4 * getYUnit());
        g2d.drawLine(getXCentre() + 3 * getXUnit(), getYCentre(), getXCentre() + 3 * getXUnit(), getYCentre() + 4 * getYUnit());
    }
}

package ukitinu.breakoutclone.objects.bricks;

import lombok.AccessLevel;
import lombok.Getter;
import ukitinu.breakoutclone.Room;

import java.awt.*;
import java.util.function.Consumer;

@Getter
public final class SpeedBrick extends AbstractBrick {
    private static final double SPEED_FAST = 0.25;
    private static final double SPEED_SLOW = -0.1;
    private static final int SCORE_FAST = 3;
    private static final int SCORE_SLOW = 1;

    private final double speedDelta;
    private final int score;

    @Getter(AccessLevel.PRIVATE)
    private Consumer<Graphics> drawer;

    private SpeedBrick(int x, int y, double speedDelta, int score) {
        super(x, y);
        this.speedDelta = speedDelta;
        this.score = score;
    }

    @Override
    public void onHit() {
        Room.INSTANCE.getBall().changeAbsoluteSpeedBy(speedDelta);
        super.onHit();
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void drawSymbol(Graphics g) {
        drawer.accept(g);
    }

    static SpeedBrick fastBrick(int x, int y) {
        SpeedBrick fastBrick = new SpeedBrick(x, y, SPEED_FAST, SCORE_FAST);
        fastBrick.drawer = g -> {
            g.setColor(Color.RED);
            g.fillPolygon(
                    new int[]{fastBrick.getXCentre() - 2 * fastBrick.getXUnit(),
                            fastBrick.getXCentre() - 2 * fastBrick.getXUnit(),
                            fastBrick.getXCentre() + 2 * fastBrick.getXUnit()},
                    new int[]{fastBrick.getYCentre() - 2 * fastBrick.getYUnit(),
                            fastBrick.getYCentre() + 2 * fastBrick.getYUnit(),
                            fastBrick.getYCentre()},
                    3);
        };
        return fastBrick;
    }

    static SpeedBrick slowBrick(int x, int y) {
        SpeedBrick slowBrick = new SpeedBrick(x, y, SPEED_SLOW, SCORE_SLOW);
        slowBrick.drawer = g -> {
            g.setColor(Color.GREEN);
            g.fillPolygon(
                    new int[]{slowBrick.getXCentre() - 2 * slowBrick.getXUnit(),
                            slowBrick.getXCentre() + 2 * slowBrick.getXUnit(),
                            slowBrick.getXCentre() + 2 * slowBrick.getXUnit()},
                    new int[]{slowBrick.getYCentre(),
                            slowBrick.getYCentre() + 2 * slowBrick.getYUnit(),
                            slowBrick.getYCentre() - 2 * slowBrick.getYUnit()},
                    3);
        };
        return slowBrick;
    }
}

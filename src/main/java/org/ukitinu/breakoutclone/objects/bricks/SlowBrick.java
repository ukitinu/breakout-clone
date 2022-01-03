package org.ukitinu.breakoutclone.objects.bricks;

import java.awt.*;

public class SlowBrick extends SpeedBrick {
    private static final double SPEED_VAR = -0.3;

    public SlowBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public double getSpeedDelta() {
        return SPEED_VAR;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public void drawSymbol(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillPolygon(
                new int[]{getXCentre() - 2 * getXUnit(), getXCentre() + 2 * getXUnit(), getXCentre() + 2 * getXUnit()},
                new int[]{getYCentre(), getYCentre() + 2 * getYUnit(), getYCentre() - 2 * getYUnit()},
                3);
    }
}

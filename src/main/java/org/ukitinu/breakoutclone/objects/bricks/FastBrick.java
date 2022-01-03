package org.ukitinu.breakoutclone.objects.bricks;

import java.awt.*;

public class FastBrick extends SpeedBrick {
    private static final double SPEED_VAR = 0.7;

    public FastBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public double getSpeedDelta() {
        return SPEED_VAR;
    }

    @Override
    public int getScore() {
        return 3;
    }

    @Override
    public void drawSymbol(Graphics g) {
        g.setColor(Color.RED);
        g.fillPolygon(
                new int[]{getXCentre() - 2 * getXUnit(), getXCentre() - 2 * getXUnit(), getXCentre() + 2 * getXUnit()},
                new int[]{getYCentre() - 2 * getYUnit(), getYCentre() + 2 * getYUnit(), getYCentre()},
                3);
    }
}

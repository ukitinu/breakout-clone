package org.ukitinu.breakoutclone.objects.bricks;

import java.awt.*;

public class FastBrick extends SpeedBrick {
    public FastBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public int getSpeedDelta() {
        return 1;
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

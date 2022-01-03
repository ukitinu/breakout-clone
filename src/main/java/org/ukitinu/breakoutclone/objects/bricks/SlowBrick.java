package org.ukitinu.breakoutclone.objects.bricks;

import java.awt.*;

public class SlowBrick extends SpeedBrick {
    public SlowBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public int getSpeedDelta() {
        return -1;
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

package ukitinu.breakoutclone.objects.bricks;

import ukitinu.breakoutclone.game.Spawner;

import java.awt.*;

public class FakePaddleBrick extends AbstractBrick {
    FakePaddleBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void drawSymbol(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(getXCentre() - 2 * getXUnit(), getYCentre() - 2 * getYUnit(), 4 * getXUnit(), 4 * getYUnit());
    }

    @Override
    public void onHit() {
        Spawner.INSTANCE.placeFakePaddle();
        super.onHit();
    }
}

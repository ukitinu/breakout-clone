package ukitinu.breakoutclone.objects.bricks;

import java.awt.*;

public class SimpleBrick extends AbstractBrick {
    SimpleBrick(int x, int y) {
        super(x, y);
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public void drawSymbol(Graphics g) {
    }
}

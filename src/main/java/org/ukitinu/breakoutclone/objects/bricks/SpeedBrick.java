package org.ukitinu.breakoutclone.objects.bricks;

import lombok.AccessLevel;
import lombok.Getter;
import org.ukitinu.breakoutclone.Room;

@Getter(AccessLevel.PROTECTED)
public abstract class SpeedBrick extends AbstractBrick {

    public SpeedBrick(int x, int y) {
        super(x, y);
    }

    protected abstract int getSpeedDelta();

    @Override
    public void onHit() {
        Room.INSTANCE.getBall().changeAbsoluteSpeedBy(getSpeedDelta());
        super.onHit();
    }

    @Override
    public abstract int getScore();
}

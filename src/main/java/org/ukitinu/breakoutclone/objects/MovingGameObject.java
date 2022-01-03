package org.ukitinu.breakoutclone.objects;

import lombok.Getter;
import lombok.Setter;
import org.ukitinu.breakoutclone.ObjectType;

@Getter
@Setter
public abstract class MovingGameObject extends AbstractGameObject {

    protected int velX;
    protected int velY;

    MovingGameObject(int x, int y, int width, int height, ObjectType type) {
        super(x, y, width, height, type);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    public void stop() {
        this.velX = 0;
        this.velY = 0;
    }

}

package ukitinu.breakoutclone.objects;


import lombok.Getter;
import lombok.Setter;
import ukitinu.breakoutclone.ObjectType;

import java.awt.*;

@Getter
@Setter
public abstract class AbstractGameObject implements GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private final ObjectType objectType;

    public AbstractGameObject(int x, int y, int width, int height, ObjectType objectType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.objectType = objectType;
    }

    @Override
    public Rectangle getCollision() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public String toString() {
        return String.format("%s(%d, %d)", objectType, x, y);
    }
}

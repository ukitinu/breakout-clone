package org.ukitinu.breakoutclone.objects;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ukitinu.breakoutclone.ObjectType;

import java.awt.Rectangle;

@Getter
@Setter
@ToString
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
}
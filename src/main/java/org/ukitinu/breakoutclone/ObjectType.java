package org.ukitinu.breakoutclone;

public enum ObjectType {
    BRICK(true),
    BALL(true),
    PADDLE(true),
    FAKE_PADDLE(true),
    MODIFIER(true);

    private final boolean collidable;

    ObjectType(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean hasCollision() {
        return collidable;
    }
}

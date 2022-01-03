package org.ukitinu.breakoutclone.collision;

import lombok.Getter;

public enum Collision {
    NONE(false),
    VERTICAL(true),
    HORIZONTAL(false),
    LEFT_SIDE(true),
    RIGHT_SIDE(true);

    Collision(boolean isVertical) {
        this.isVertical = isVertical;
    }

    @Getter
    private final boolean isVertical;

}


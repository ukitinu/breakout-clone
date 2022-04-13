package ukitinu.breakoutclone.collision;

import lombok.Getter;

public enum Collision {
    NONE(false),
    HORIZONTAL(true), // generic, when the intersection geometry width is larger
    VERTICAL(false), // generic, when the intersection geometry height is larger
    LEFT_SIDE(true),
    RIGHT_SIDE(true);

    Collision(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    @Getter
    private final boolean isHorizontal;

}


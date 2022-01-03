package org.ukitinu.breakoutclone.collision;

import java.awt.Rectangle;

public interface HasCollision {

    Rectangle getCollision();

    default Collision findCollision(HasCollision other) {
        Rectangle r = getCollision().intersection(other.getCollision());
        return r.isEmpty() ? Collision.NONE : r.width >= r.height ? Collision.VERTICAL : Collision.HORIZONTAL;
    }
}

package org.ukitinu.breakoutclone.collision;

import java.awt.*;

public interface Collidable {

    Rectangle getCollision();

    /**
     * To be overridden if the object wants to specify a more detailed geometry.
     */
    default Collision findCollision(Collidable target) {
        if (target == this) return Collision.NONE;
        Rectangle r = getCollision().intersection(target.getCollision());
        return r.isEmpty() ? Collision.NONE : r.width >= r.height ? Collision.VERTICAL : Collision.HORIZONTAL;
    }

    default void onHit() {
        //do nothing
    }
}

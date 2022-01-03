package org.ukitinu.breakoutclone.objects;

import org.ukitinu.breakoutclone.Drawable;
import org.ukitinu.breakoutclone.ObjectType;
import org.ukitinu.breakoutclone.collision.HasCollision;

public interface GameObject extends Drawable, HasCollision {

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    ObjectType getObjectType();
}

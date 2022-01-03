package ukitinu.breakoutclone.objects;

import ukitinu.breakoutclone.Drawable;
import ukitinu.breakoutclone.ObjectType;
import ukitinu.breakoutclone.collision.Collidable;

public interface GameObject extends Drawable, Collidable {

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    ObjectType getObjectType();
}

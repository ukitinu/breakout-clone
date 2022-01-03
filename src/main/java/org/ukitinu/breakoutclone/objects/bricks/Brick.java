package org.ukitinu.breakoutclone.objects.bricks;

import org.ukitinu.breakoutclone.Drawable;
import org.ukitinu.breakoutclone.HUD;
import org.ukitinu.breakoutclone.Room;
import org.ukitinu.breakoutclone.Spawner;
import org.ukitinu.breakoutclone.collision.Collidable;
import org.ukitinu.breakoutclone.objects.GameObject;
import org.ukitinu.breakoutclone.objects.HasScore;

import java.awt.*;

public interface Brick extends GameObject, Drawable, Collidable, HasScore {

    int WIDTH = 80;
    int HEIGHT = 24;

    void drawSymbol(Graphics g);

    /**
     * Removes 'default' to force implementation.
     */
    @Override
    default void onHit() {
        Spawner.INSTANCE.placeModifier(getX() + Brick.WIDTH / 2, getY() + Brick.HEIGHT / 2);
        HUD.INSTANCE.updateScore(this);
        Room.INSTANCE.remove(this);
    }

    @Override
    default void tick() {
    }
}

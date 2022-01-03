package org.ukitinu.breakoutclone.objects.bricks;

import org.ukitinu.breakoutclone.Drawable;
import org.ukitinu.breakoutclone.collision.Collidable;
import org.ukitinu.breakoutclone.objects.GameObject;
import org.ukitinu.breakoutclone.objects.HasScore;

import java.awt.Graphics;

public interface Brick extends GameObject, Drawable, Collidable, HasScore {

    int WIDTH = 80;
    int HEIGHT = 24;

    void drawSymbol(Graphics g);

    /**
     * Forces implementation.
     */
    @Override
    void onHit();

    @Override
    default void tick() {
    }
}

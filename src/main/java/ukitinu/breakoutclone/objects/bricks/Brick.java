package ukitinu.breakoutclone.objects.bricks;

import ukitinu.breakoutclone.Drawable;
import ukitinu.breakoutclone.HUD;
import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.Spawner;
import ukitinu.breakoutclone.collision.Collidable;
import ukitinu.breakoutclone.objects.GameObject;
import ukitinu.breakoutclone.objects.HasScore;

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

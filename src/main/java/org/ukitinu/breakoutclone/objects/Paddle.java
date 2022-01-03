package org.ukitinu.breakoutclone.objects;

import org.ukitinu.breakoutclone.Game;
import org.ukitinu.breakoutclone.ObjectType;
import org.ukitinu.breakoutclone.Utils;
import org.ukitinu.breakoutclone.collision.Collidable;
import org.ukitinu.breakoutclone.collision.Collision;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class Paddle extends MovingGameObject {
    private static final int SIDE_WIDTH = 64 / 8;
    public static final int WIDTH = SIDE_WIDTH * 8;
    public static final int HEIGHT = 6;
    private static final int DEFAULT_SPEED = 4;

    public Paddle(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.PADDLE);
    }

    @Override
    public void tick() {
        super.tick();
        x = Utils.minMax(0, x, Game.WIDTH - width - width / 5 - 1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x + SIDE_WIDTH, y, SIDE_WIDTH * 6, height);
        g.fillPolygon(new int[]{x, x + SIDE_WIDTH, x + SIDE_WIDTH}, new int[]{y + HEIGHT, y + HEIGHT, y}, 3);
        g.fillPolygon(new int[]{x + SIDE_WIDTH * 7, x + SIDE_WIDTH * 7, x + WIDTH}, new int[]{y, y + HEIGHT, y + HEIGHT}, 3);
    }

    @Override
    public Collision findCollision(Collidable target) {
        Collision basicCollision = super.findCollision(target);
        if (basicCollision == Collision.NONE) return basicCollision;
        Rectangle r = target.getCollision();
        int xMiddle = r.x + (r.width + 1) / 2;
        if (xMiddle <= x + SIDE_WIDTH) return Collision.LEFT_SIDE;
        if (xMiddle >= x + SIDE_WIDTH * 7) return Collision.RIGHT_SIDE;
        return Collision.HORIZONTAL;
    }

    public void onKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            velX = -DEFAULT_SPEED;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            velX = DEFAULT_SPEED;
        }
    }

    public void onKeyReleased(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT && velX < 0 || keyCode == KeyEvent.VK_RIGHT && velX > 0) {
            stop();
        }
    }
}

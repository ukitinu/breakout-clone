package org.ukitinu.breakoutclone.objects;

import org.ukitinu.breakoutclone.*;
import org.ukitinu.breakoutclone.collision.Collision;
import org.ukitinu.breakoutclone.objects.bricks.Brick;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public final class Ball extends MovingGameObject {
    public static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final Random RANDOM = new Random();
    private static final int[] X_VEL = {-3, 3};
    public static final int MIN_SPEED = 2;
    private static final int MAX_SPEED = 7;

    public Ball(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.BALL);
        setVelX(X_VEL[RANDOM.nextInt(X_VEL.length)]);
        setVelY(-3);
    }

    @Override
    public void tick() {
        checkPaddleCollision();
        checkFakePaddleCollision();
        checkBrickCollision();

        if (y >= Game.HEIGHT - height * 3) {
            velY = -Math.abs(velY);
            HUD.INSTANCE.dropLife();
        } else if (y <= HUD.HEIGHT) {
            velY = Math.abs(velY);
        } else if (x <= 0) {
            velX = Math.abs(velX);
        } else if (x >= Game.WIDTH - width * 2) {
            velX = -Math.abs(velX);
        }

        super.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
    }

    public void changeAbsoluteSpeedBy(double val) {
        velY = velY > 0 ? Utils.minMax(MIN_SPEED, velY + val, MAX_SPEED) : Utils.minMax(-MAX_SPEED, velY - val, -MIN_SPEED);
    }

    private void checkPaddleCollision() {
        Collision paddleCollision = Room.INSTANCE.getPlayerPaddle().findCollision(this);
        if (paddleCollision != Collision.NONE) {
            velY = -velY;
            if (paddleCollision == Collision.LEFT_SIDE && velX > 0) velX = -velX;
            else if (paddleCollision == Collision.RIGHT_SIDE && velX < 0) velX = -velX;
            adjustY();
        }
    }

    private void adjustY() {
        Paddle paddle = Room.INSTANCE.getPlayerPaddle();
        int delta;
        if (y > paddle.getY()) {
            //ball under paddle, delta > 0, lower ball
            delta = paddle.getY() + paddle.getHeight() - y;
        } else {
            //ball not under paddle, delta < 0, raise ball
            delta = paddle.getY() - y - height;
        }
        y += delta;
    }

    private void checkFakePaddleCollision() {
        for (FakePaddle fakePaddle : Room.INSTANCE.getFakePaddles()) {
            Collision collision = findCollision(fakePaddle);
            if (collision != Collision.NONE) {
                fakePaddle.onHit();
                if (collision.isVertical()) velY = -velY;
                else velX = -velX;
                break;
            }
        }
    }

    private void checkBrickCollision() {
        for (Brick brick : Room.INSTANCE.getBricks()) {
            Collision collision = findCollision(brick);
            if (collision != Collision.NONE) {
                brick.onHit();
                if (collision.isVertical()) velY = -velY;
                else velX = -velX;
                break;
            }
        }
    }

}

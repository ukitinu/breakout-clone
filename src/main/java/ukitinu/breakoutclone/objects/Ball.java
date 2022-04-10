package ukitinu.breakoutclone.objects;

import ukitinu.breakoutclone.ObjectType;
import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.Utils;
import ukitinu.breakoutclone.collision.Collision;
import ukitinu.breakoutclone.game.Game;
import ukitinu.breakoutclone.game.GameConst;
import ukitinu.breakoutclone.gui.HUD;

import java.awt.*;
import java.util.List;
import java.util.Random;

public final class Ball extends MovingGameObject {
    public static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final Random RANDOM = new Random();
    private static final int[] X_VEL = {-3, 3};
    public static final int MIN_SPEED = 3;
    private static final int MAX_SPEED = 7;
    private static final int START_SPEED = 3;
    private static final ObjectType[] OBSTACLES = {ObjectType.BRICK, ObjectType.PADDLE, ObjectType.FAKE_PADDLE};

    public Ball(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.BALL);
        setVelX(X_VEL[RANDOM.nextInt(X_VEL.length)]);
        setVelY(-START_SPEED);
    }

    @Override
    public void tick() {
        checkCollision();

        if (y >= GameConst.HEIGHT - height * 3) {
            velY = -Math.abs(velY);
            Game.lives--;
        } else if (y <= HUD.HEIGHT) {
            velY = Math.abs(velY);
        } else if (x <= 0) {
            velX = Math.abs(velX);
        } else if (x >= GameConst.WIDTH - width * 2) {
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

    private void checkCollision() {
        List<GameObject> collidables = Room.INSTANCE.getCollidables(OBSTACLES);
        for (GameObject o : collidables) {
            Collision collision = o.findCollision(this);
            if (collision != Collision.NONE) {
                o.onHit();
                if (o instanceof Paddle) {
                    velY = -velY;
                    if (collision == Collision.LEFT_SIDE && velX > 0) velX = -velX;
                    else if (collision == Collision.RIGHT_SIDE && velX < 0) velX = -velX;
                    adjustY();
                } else {
                    if (collision.isVertical()) velY = -velY;
                    else velX = -velX;
                }
                break;
            }
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

}

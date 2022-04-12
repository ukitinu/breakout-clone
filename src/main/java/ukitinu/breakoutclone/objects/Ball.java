package ukitinu.breakoutclone.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.random.RandomGenerator;

public final class Ball extends MovingGameObject {
    private static final Logger LOG = LogManager.getLogger(Ball.class);

    public static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final RandomGenerator RANDOM = new Random();
    private static final double[] X_VEL = {-3, -2.7, 2.7, 3};
    public static final double MIN_SPEED = 2.5;
    private static final double MAX_SPEED = 6.5;
    private static final double START_SPEED = 3;
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
            Game.loseLife();
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
        val /= 2; // split between x and y
        velY = velY > 0 ? Utils.minMax(MIN_SPEED, velY + val, MAX_SPEED) : Utils.minMax(-MAX_SPEED, velY - val, -MIN_SPEED);
        velX = velX > 0 ? Utils.minMax(MIN_SPEED, velX + val, MAX_SPEED) : Utils.minMax(-MAX_SPEED, velX - val, -MIN_SPEED);
    }

    private void checkCollision() {
        List<GameObject> collidables = Room.INSTANCE.getCollidables(OBSTACLES);
        for (GameObject o : collidables) {
            Collision collision = o.findCollision(this);
            if (collision != Collision.NONE) {
                LOG.info("{} collision with {}", collision, o);
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

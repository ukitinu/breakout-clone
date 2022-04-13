package ukitinu.breakoutclone.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Conf;
import ukitinu.breakoutclone.ObjectType;
import ukitinu.breakoutclone.Utils;
import ukitinu.breakoutclone.collision.Collidable;
import ukitinu.breakoutclone.collision.Collision;
import ukitinu.breakoutclone.game.GameConst;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class Paddle extends MovingGameObject {
    private static final Logger LOG = LogManager.getLogger(Paddle.class);

    private static final int SIDE_WIDTH = 64 / 8;
    public static final int WIDTH = SIDE_WIDTH * 8;
    public static final int HEIGHT = 6;
    private static final double DEFAULT_SPEED = 4.5;

    public Paddle(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.PADDLE);
    }

    @Override
    public void tick() {
        super.tick();
        x = Utils.minMax(0, x, GameConst.WIDTH - width - width / 5 - 1);
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
        Collision collision;
        if (xMiddle <= x + SIDE_WIDTH) {
            collision = Collision.LEFT_SIDE;
        } else if (xMiddle >= x + SIDE_WIDTH * 7) {
            collision = Collision.RIGHT_SIDE;
        } else {
            collision = Collision.VERTICAL;
        }
        if(Conf.LOG_PHYSICS.bool()) LOG.info("{} collision with {}", collision, target);
        return collision;
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

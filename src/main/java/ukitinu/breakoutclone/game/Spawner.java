package ukitinu.breakoutclone.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Conf;
import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.gui.HUD;
import ukitinu.breakoutclone.objects.Ball;
import ukitinu.breakoutclone.objects.FakePaddle;
import ukitinu.breakoutclone.objects.Modifier;
import ukitinu.breakoutclone.objects.Paddle;
import ukitinu.breakoutclone.objects.bricks.Brick;
import ukitinu.breakoutclone.objects.bricks.BrickFactory;

public enum Spawner {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Spawner.class);

    private static final int PADDLE_SPAWN_X = (GameConst.WIDTH - Paddle.WIDTH) / 2;
    private static final int PADDLE_SPAWN_Y = GameConst.HEIGHT - 64;
    private static final int PADDLE_SPAWN_Y_DIST = 64;
    private static final int FAKE_PADDLE_SPAWN_Y = GameConst.HEIGHT - 64 * 3;

    private static final int BALL_SPAWN_X = (GameConst.WIDTH - Ball.WIDTH) / 2;
    private static final int BALL_SPAWN_Y = GameConst.HEIGHT - PADDLE_SPAWN_Y_DIST * 2;

    private static final int BRICK_COLUMNS = (GameConst.WIDTH - 20) / Brick.WIDTH;
    public static final int BRICK_SPAWN_X = (GameConst.WIDTH - Brick.WIDTH * BRICK_COLUMNS) / 2;
    public static final int BRICK_SPAWN_Y = HUD.HEIGHT + 20;


    public void placeBall() {
        if (Conf.LOG_GAME.bool()) LOG.info("Placing ball at {},{}", BALL_SPAWN_X, BALL_SPAWN_Y);
        Room.INSTANCE.add(new Ball(BALL_SPAWN_X, BALL_SPAWN_Y));
    }

    public void placePaddle() {
        if (Conf.LOG_GAME.bool()) LOG.info("Placing paddle at {},{}", PADDLE_SPAWN_X, PADDLE_SPAWN_Y);
        Room.INSTANCE.add(new Paddle(PADDLE_SPAWN_X, PADDLE_SPAWN_Y));
    }

    public void placeFakePaddle() {
        if (Conf.LOG_GAME.bool()) LOG.info("Placing fake paddle at {},{}", PADDLE_SPAWN_X, FAKE_PADDLE_SPAWN_Y);
        Room.INSTANCE.add(new FakePaddle(PADDLE_SPAWN_X, FAKE_PADDLE_SPAWN_Y));
    }

    public void placeBricks(int rows) {
        BrickFactory factory = new BrickFactory(rows, BRICK_COLUMNS);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < BRICK_COLUMNS; column++) {
                int x = BRICK_SPAWN_X + Brick.WIDTH * column;
                int y = BRICK_SPAWN_Y + Brick.HEIGHT * row;
                Brick brick = factory.buildBrick(row, column, x, y);
                if (Conf.LOG_GAME.bool()) LOG.info("Placing brick {},{}: {}", row, column, brick.getClass().getSimpleName());
                Room.INSTANCE.add(brick);
            }
        }
    }

    public void placeModifier(int x, int y) {
        if (Conf.LOG_GAME.bool()) LOG.info("Placing modifier at {},{}", x, y);
        Room.INSTANCE.add(new Modifier(x, y));
    }
}

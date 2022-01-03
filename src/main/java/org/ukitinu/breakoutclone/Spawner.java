package org.ukitinu.breakoutclone;

import org.ukitinu.breakoutclone.objects.Ball;
import org.ukitinu.breakoutclone.objects.FakePaddle;
import org.ukitinu.breakoutclone.objects.Modifier;
import org.ukitinu.breakoutclone.objects.Paddle;
import org.ukitinu.breakoutclone.objects.bricks.Brick;
import org.ukitinu.breakoutclone.objects.bricks.BrickFactory;

public enum Spawner {
    INSTANCE;

    private static final int PADDLE_SPAWN_X = (Game.WIDTH - Paddle.WIDTH) / 2;
    private static final int PADDLE_SPAWN_Y = Game.HEIGHT - 64;
    private static final int PADDLE_SPAWN_Y_DIST = 64;
    private static final int FAKE_PADDLE_SPAWN_Y = Game.HEIGHT - 64 * 3;

    private static final int BALL_SPAWN_X = (Game.WIDTH - Ball.WIDTH) / 2;
    private static final int BALL_SPAWN_Y = Game.HEIGHT - PADDLE_SPAWN_Y_DIST * 2;

    private static final int BRICK_COLUMNS = (Game.WIDTH - 20) / Brick.WIDTH;
    public static final int BRICK_SPAWN_X = (Game.WIDTH - Brick.WIDTH * BRICK_COLUMNS) / 2;
    public static final int BRICK_SPAWN_Y = HUD.HEIGHT + 20;


    public void placeBall() {
        Room.INSTANCE.add(new Ball(BALL_SPAWN_X, BALL_SPAWN_Y));
    }

    public void placePaddle() {
        Room.INSTANCE.add(new Paddle(PADDLE_SPAWN_X, PADDLE_SPAWN_Y));
    }

    public void placeFakePaddle() {
        Room.INSTANCE.add(new FakePaddle(PADDLE_SPAWN_X, FAKE_PADDLE_SPAWN_Y));
    }

    public void placeBricks(int rows) {
        BrickFactory factory = new BrickFactory(rows, BRICK_COLUMNS);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < BRICK_COLUMNS; column++) {
                int x = BRICK_SPAWN_X + Brick.WIDTH * column;
                int y = BRICK_SPAWN_Y + Brick.HEIGHT * row;
                Brick brick = factory.buildBrick(row, column, x, y);
                Room.INSTANCE.add(brick);
            }
        }
    }

    public void placeModifier(int x, int y) {
        Room.INSTANCE.add(new Modifier(x, y));
    }
}

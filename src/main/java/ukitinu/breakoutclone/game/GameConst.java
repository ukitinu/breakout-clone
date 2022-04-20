package ukitinu.breakoutclone.game;

import ukitinu.breakoutclone.Conf;

public final class GameConst {
    private GameConst() {
        throw new IllegalStateException("non-instantiable");
    }

    /* gui */
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;

    /* fps */
    private static final int TARGET_FPS = Conf.FPS_TARGET.num();
    static final int TARGET_FPS_LOW = Conf.FPS_TARGET.num();
    static final int TARGET_FPS_HIGH = Conf.FPS_MAX.num();
    private static final long NANOS_SEC = 1000_000_000;
    static final long OPTIMAL_TIME = NANOS_SEC / TARGET_FPS;

    /* other */
    public static final int MAX_LIVES = Conf.MAX_LIVES.num();
    public static final int MAX_LEVEL = Conf.MAX_LEVEL.num();
}
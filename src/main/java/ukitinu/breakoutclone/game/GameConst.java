package ukitinu.breakoutclone.game;

import ukitinu.breakoutclone.Conf;
import ukitinu.breakoutclone.Utils;

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
    public static final int MAX_LIVES = Utils.minMax(1, Conf.MAX_LIVES.num(), 10);
    public static final int MAX_LEVEL = Utils.minMax(1, Conf.MAX_LEVEL.num(), 5);
}
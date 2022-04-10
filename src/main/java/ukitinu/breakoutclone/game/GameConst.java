package ukitinu.breakoutclone.game;

public final class GameConst {
    private GameConst() {
        throw new IllegalStateException("non-instantiable");
    }

    /* gui */
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;

    /* fps */
    private static final int TARGET_FPS = 60;
    static final int TARGET_FPS_LOW = 60;
    static final int TARGET_FPS_HIGH = 80;
    private static final long NANOS_SEC = 1000_000_000;
    static final long OPTIMAL_TIME = NANOS_SEC / TARGET_FPS;

    /* other */
    public static final int MAX_LIVES = 3;
}
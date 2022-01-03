package org.ukitinu.breakoutclone;

public final class GameConst {
    private GameConst() {
        throw new IllegalStateException("non-instantiable");
    }

    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;

    private static final int TARGET_FPS = 60;
    private static final long NANOS_SEC = 1000_000_000;
    static final long OPTIMAL_TIME = NANOS_SEC / TARGET_FPS;
}
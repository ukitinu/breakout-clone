package ukitinu.breakoutclone.game;

import ukitinu.breakoutclone.Room;

public final class Game {
    public static int level;
    public static int score;
    public static int lives;
    public static boolean lifeLost;
    public static String message;

    private Game() {
    }

    static void init() {
        init(false);
    }

    private static void init(boolean lost) {
        level = 1;
        score = 0;
        lives = GameConst.MAX_LIVES;
        lifeLost = lost;

        initLevel();
    }

    public static void loseLife() {
        lives--;
        score = 0;
        lifeLost = true;

        if (lives > 0) {
            initLevel();
        } else {
            init(true);
        }
    }

    static void nextLevel() {
        if (lives < GameConst.MAX_LIVES) {
            lives++;
        } else {
            score += level * 3;
        }
        level++;
        if (level <= GameConst.MAX_LEVEL) {
            initLevel();
        } else {
            Room.INSTANCE.clear();
            Room.INSTANCE.tick();
            GameManager.INSTANCE.switchGameState();

            Room.INSTANCE.tick();
        }
    }

    private static void placeBasics() {
        Spawner.INSTANCE.placeBricks(level * 2);
        Spawner.INSTANCE.placeBall();
        Spawner.INSTANCE.placePaddle();
    }

    private static void initLevel() {
        Room.INSTANCE.clear();
        Room.INSTANCE.tick();

        Game.placeBasics();
        GameManager.INSTANCE.switchGameState();

        Room.INSTANCE.tick();
    }
}

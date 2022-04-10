package ukitinu.breakoutclone.game;

import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.Utils;

public final class Game {
    public static int level;
    public static int score;
    public static int lives;
    public static boolean isOk;

    private Game() {
    }

    static void init() {
        level = 1;
        score = 0;
        lives = GameConst.MAX_LIVES;
        isOk = true;

        initLevel();
    }

    public static void loseLife() {
        lives--;
        score = Utils.minMax(0, score - level * 3, score);
        isOk = false;

        initLevel();
    }

    static void nextLevel() {
        if (lives < GameConst.MAX_LIVES) {
            lives++;
        } else {
            score += level * 3;
        }
        level++;

        initLevel();
    }

    private static void placeBasics() {
        Spawner.INSTANCE.placeBricks(level + 1);
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

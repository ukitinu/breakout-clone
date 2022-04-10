package ukitinu.breakoutclone.game;

public final class Game {
    public static int level;
    public static int score;
    public static int lives;

    private Game() {
    }

    public static void init() {
        level = 1;
        score = 0;
        lives = GameConst.MAX_LIVES;
    }
}

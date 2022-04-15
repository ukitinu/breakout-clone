package ukitinu.breakoutclone.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ukitinu.breakoutclone.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Game {
    private static final Logger LOG = LogManager.getLogger(Game.class);

    public static int level;
    public static int score;
    public static int lives;

    /**
     * When populated, pauses the game and shows the message.
     * Essentially, it is an observable but since the sole observer is the Menu class,
     * there's no need to implement the whole pattern.
     */
    private static final List<String> alert = new ArrayList<>();

    private Game() {
    }

    static void init() {
        LOG.debug("Initialising game");
        level = 1;
        score = 0;
        lives = GameConst.MAX_LIVES;

        LOG.debug("Initialising level " + level);
        renewGame(Game::placeBasics);
    }

    public static boolean hasAlert() {
        return !alert.isEmpty();
    }

    public static List<String> getAlert() {
        return alert;
    }

    public static void clearAlert() {
        alert.clear();
    }

    public static void setAlert(Collection<String> alert) {
        Game.alert.addAll(alert);
        GameManager.INSTANCE.switchGameState();
    }

    public static void loseLife() {
        lives--;
        score = 0;

        if (lives > 0) {
            LOG.debug("Initialising level " + level);
            renewGame(Game::placeBasics);

            setAlert(List.of("Life lost!", "Level restarted and score reset"));
        } else {
            init();
            setAlert(List.of("Game lost!", "Full reset"));
        }
    }

    static void nextLevel() {
        if (lives < GameConst.MAX_LIVES) {
            lives++;
        } else {
            score += level * 10;
        }

        level++;

        if (level <= GameConst.MAX_LEVEL) {
            LOG.debug("Initialising level " + level);
            renewGame(Game::placeBasics);
            setAlert(List.of("Level cleared!", "Starting new level"));
        } else {
            renewGame(null);
            setAlert(List.of("Congratulations!", "You won with a score of " + score));
        }
    }

    private static void placeBasics() {
        LOG.debug("Populating window");
        Spawner.INSTANCE.placeBricks(level * 2);
        Spawner.INSTANCE.placeBall();
        Spawner.INSTANCE.placePaddle();
    }

    private static void renewGame(Action afterClear) {
        Room.INSTANCE.clear();
        Room.INSTANCE.tick();
        if (afterClear != null) afterClear.exec();

        Room.INSTANCE.tick();
    }

    @FunctionalInterface
    private interface Action {
        void exec();
    }
}

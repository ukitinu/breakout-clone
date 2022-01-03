package ukitinu.breakoutclone.game;

public final class GameManager {
    private GameManager() {
        throw new IllegalStateException("non-instantiable");
    }

    private static final Game GAME = new Game();

    public static void main(String[] args) {
        GAME.start();
    }

    public static void switchGameState(){
        GAME.switchState();
    }

    public static int getCurrentLevel(){
        return GAME.getLevel();
    }
}

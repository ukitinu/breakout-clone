package ukitinu.breakoutclone.game;

public enum GameManager {
    INSTANCE;

    private final Game GAME = new Game();

    public void start() {
        GAME.start();
    }

    public void switchGameState() {
        GAME.switchState();
    }

    public int getCurrentLevel() {
        return GAME.getLevel();
    }
}

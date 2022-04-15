package ukitinu.breakoutclone.game;

public enum GameManager {
    INSTANCE;

    private final GameThread THREAD = new GameThread();

    public void start() {
        Game.init();
        THREAD.start();
    }

    public void switchGameState() {
        THREAD.switchState();
    }

    public void setGameState(GameState state) {
        THREAD.setState(state);
    }
}

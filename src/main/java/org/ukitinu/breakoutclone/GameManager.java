package org.ukitinu.breakoutclone;

public final class GameManager {
    private GameManager() {
        throw new IllegalStateException("non-instantiable");
    }

    private static final Game GAME = new Game();

    public static void main(String[] args) {
        GAME.start();
    }

    static void switchGameState(){
        GAME.switchState();
    }

    static int getCurrentLevel(){
        return GAME.getLevel();
    }
}

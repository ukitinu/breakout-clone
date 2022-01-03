package org.ukitinu.breakoutclone;

public final class Main {
    private Main() {
        throw new IllegalStateException("non-instantiable");
    }

    public static void main(String[] args) {
        Game game = new Game("Breakout clone");
        game.start();
    }
}

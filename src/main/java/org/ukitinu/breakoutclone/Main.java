package org.ukitinu.breakoutclone;

public final class Main {
    private Main() {
        throw new IllegalStateException("non-instantiable");
    }

    static final Game GAME = new Game("Breakout clone");

    public static void main(String[] args) {
        GAME.start();
    }
}

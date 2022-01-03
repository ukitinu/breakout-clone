package org.ukitinu.breakoutclone.objects.bricks;

import java.util.HashMap;
import java.util.Map;

public final class BrickFactory {
    private final int rows;
    private final int columns;
    private final Map<BrickType, Integer> counter = new HashMap<>();
    private final Map<BrickType, Integer> max = new HashMap<>();

    public BrickFactory(int rows, int columns) {
        if (rows <= 0 || columns <= 0) throw new IllegalArgumentException("Rows and columns must be >= 1");
        this.rows = rows;
        this.columns = columns;
        for (BrickType type : BrickType.values()) {
            this.counter.put(type, 0);
            this.max.put(type, (int) (rows * columns * type.getRatio()));
        }
    }

    public Brick buildBrick(int row, int column, int x, int y) {
        BrickType brickType;
        do {
            brickType = BrickType.random();
        } while (counter.get(brickType) >= max.get(brickType) || !brickType.isOk(row, column, rows, columns));

        counter.put(brickType, counter.get(brickType) + 1);
        var constructor = brickType.getConstructor();
        return constructor.apply(x, y);
    }
}

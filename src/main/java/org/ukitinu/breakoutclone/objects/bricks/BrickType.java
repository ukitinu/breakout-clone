package org.ukitinu.breakoutclone.objects.bricks;

import lombok.AccessLevel;
import lombok.Getter;
import org.ukitinu.breakoutclone.Utils;

import java.util.Random;
import java.util.function.BiFunction;

@Getter(AccessLevel.PACKAGE)
public enum BrickType {
    SIMPLE(SimpleBrick::new, 1, 10),
    FAST(FastBrick::new, 0.1, 1),
    SLOW(SlowBrick::new, 0.1, 1),
    WALL(WallBrick::new, 0.1, 1),
    FAKE_PADDLE(FakePaddleBrick::new, 0.05, 2);

    private static final Random RANDOM = new Random();
    private static final BrickType[] ARRAY = buildArray();

    private final BiFunction<Integer, Integer, ? extends Brick> constructor;
    private final double ratio;
    private final int chance;

    BrickType(BiFunction<Integer, Integer, ? extends Brick> constructor, double ratio, int chance) {
        this.constructor = constructor;
        this.ratio = ratio;
        this.chance = chance;
    }

    boolean isOk(int row, int column, int rows, int columns) {
        switch (this) {
            case WALL:
                return row > 0 && column > 0 && column < columns - 1;
            case SLOW:
                return row < rows - 1;
            case FAKE_PADDLE:
                return row <= 1 && row < rows - 1;
            default:
                return true;
        }
    }

    static BrickType random() {
        int pick = RANDOM.nextInt(getChanceSum());
        return ARRAY[pick];
    }

    private static BrickType[] buildArray() {
        BrickType[] array = new BrickType[getChanceSum()];
        int from = 0;
        for (BrickType type : values()) {
            Utils.setRange(array, type, from, from + type.chance);
            from = from + type.chance;
        }
        return array;
    }

    private static int getChanceSum() {
        int sum = 0;
        for (BrickType type : values()) sum += type.chance;
        return sum;
    }
}

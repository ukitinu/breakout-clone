package ukitinu.breakoutclone.objects.bricks;

import ukitinu.breakoutclone.Utils;
import ukitinu.breakoutclone.game.Game;

import java.util.Random;
import java.util.function.BiFunction;

public enum BrickType {
    SIMPLE(SimpleBrick::new, 1, 10),
    FAST(SpeedBrick::fastBrick, 0.06, 1),
    SLOW(SpeedBrick::slowBrick, 0.04, 1),
    WALL(WallBrick::new, 0.1, 1),
    FAKE_PADDLE(FakePaddleBrick::new, 0.05, 2);

    private static final Random RANDOM = new Random();

    private final BiFunction<Integer, Integer, ? extends Brick> constructor;
    private final double ratio;
    private final int chance;

    BrickType(BiFunction<Integer, Integer, ? extends Brick> constructor, double ratio, int chance) {
        this.constructor = constructor;
        this.ratio = ratio;
        this.chance = chance;
    }

    public double getRatio() {
        return this == SIMPLE ? ratio : ratio * (1 + 0.2 * Game.level);
    }

    public BiFunction<Integer, Integer, ? extends Brick> getConstructor() {
        return constructor;
    }

    private int wChance() {
        return this == SIMPLE ? chance : (int) (chance * (1 + 0.4 * Game.level));
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
        return brickArray()[pick];
    }

    private static BrickType[] brickArray() {
        BrickType[] array = new BrickType[getChanceSum()];
        int from = 0;
        for (BrickType type : values()) {
            Utils.setRange(array, type, from, from + type.wChance());
            from = from + type.wChance();
        }
        return array;
    }

    private static int getChanceSum() {
        int sum = 0;
        for (BrickType type : values()) sum += type.wChance();
        return sum;
    }
}

package ukitinu.breakoutclone;

public final class Utils {
    private Utils() {
    }

    public static int minMax(int min, int val, int max) {
        return Math.max(min, Math.min(val, max));
    }

    public static double minMax(double min, double val, double max) {
        return Math.max(min, Math.min(val, max));
    }

    public static <T> void setRange(T[] array, T element, int from, int to) {
        if (from < 0 || to > array.length)
            throw new ArrayIndexOutOfBoundsException(String.format("size %s; from %s; to %s", array.length, from, to));

        for (int i = from; i < to; i++) array[i] = element;
    }
}

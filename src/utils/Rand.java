package utils;

/**
 * Used to generate pseudorandom numbers in a fast way
 */
public class Rand {
    private static int seed = (int) System.currentTimeMillis();

    private Rand() {
        // This class is only used with the static way
    }

    private static int fastrand() {
        seed = (214013 * seed + 2531011);
        return (seed >> 16) & 0x7FFF;
    }

    public static int randInt(int max) {
        return fastrand() % max;
    }

    public static int randInt(int a, int b) {
        return a + randInt(b - a);
    }
}

package app.util;

public class IdGenerator {
    private static int id = 1;

    public static synchronized int generate() {
        return id++;
    }
}

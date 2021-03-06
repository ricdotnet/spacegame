package Util;

public class Time {

    public static final long NS_PER_SECOND = 1000000000; //nanoseconds per second
    public static final long NS_PER_MS = 1000000; //milliseconds per second

    public static final double TPS = 60.0; // ticks per second
    public static final double NS_PER_TICK = 1000000000 / TPS; // nanoseconds per tick

    // return current time in nanoseconds
    public long nsNow() {
        return System.nanoTime();
    }

    // return current time in milliseconds
    public long msNow() {
        return System.currentTimeMillis();
    }

}

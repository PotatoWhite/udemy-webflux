package me.potato.udemywebflux.service;

public class SleepUtil {
    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (Exception ignored) {}
    }
}

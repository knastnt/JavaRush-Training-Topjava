package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        boolean fromStart = startTime==null || lt.compareTo(startTime) >= 0;
        boolean tillEnd = endTime==null || lt.compareTo(endTime) < 0;
        return fromStart && tillEnd;
    }
}

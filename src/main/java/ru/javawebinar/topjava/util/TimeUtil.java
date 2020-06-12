package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        boolean fromStart = startTime==null || lt.compareTo(startTime) >= 0;
        boolean tillEnd = endTime==null || lt.compareTo(endTime) < 0;
        return fromStart && tillEnd;
    }

    public static LocalDateTime parseToLocalDateTime(String s) {
        return LocalDateTime.parse(s, formatter);
    }
}

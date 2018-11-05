package com.moon.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class TimeUtil {
    private TimeUtil() {
        noInstanceError();
    }

    public final static LocalDate toDate(Calendar calendar) {
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public final static LocalTime toTime(Calendar calendar) {
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public final static LocalDateTime toDateTime(Calendar calendar) {
        return LocalDateTime.of(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public final static LocalDate toDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return toDate(calendar);
    }

    public final static LocalTime toTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return toTime(calendar);
    }

    public final static LocalDateTime toDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return toDateTime(calendar);
    }

    public final static boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1 == null || date2 == null ? false : date1.isBefore(date2);
    }

    public final static boolean isAfter(LocalDate date1, LocalDate date2) {
        return date1 == null || date2 == null ? false : date1.isAfter(date2);
    }
}

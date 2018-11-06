package com.moon.time;

import com.moon.util.function.IntBiFunction;

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

    /*
     * ----------------------------------------------------------------------------------
     * for each
     * ----------------------------------------------------------------------------------
     */

    public final static void forEachYears(LocalDate begin, LocalDate end, IntBiFunction<LocalDate, Boolean> consumer) {
        for (; begin.isBefore(end) && consumer.apply(begin.getYear(), begin); begin = begin.plusYears(1)) {
        }
    }

    public final static void forEachMonths(LocalDate begin, LocalDate end, IntBiFunction<LocalDate, Boolean> consumer) {
        for (; begin.isBefore(end) && consumer.apply(begin.getMonthValue(), begin); begin = begin.plusMonths(1)) {
        }
    }

    public final static void forEachDays(LocalDate begin, LocalDate end, IntBiFunction<LocalDate, Boolean> consumer) {
        for (; begin.isBefore(end) && consumer.apply(begin.getDayOfMonth(), begin); begin = begin.plusDays(1)) {
        }
    }

    public final static void forEachHours(LocalTime begin, LocalTime end, IntBiFunction<LocalTime, Boolean> consumer) {
        LocalDate now = LocalDate.now();
        LocalDateTime last = begin.isBefore(end) ? LocalDateTime.of(now, end) : LocalDateTime.of(now.plusDays(1), end);
        for (LocalDateTime start = LocalDateTime.of(now, begin);
             start.isBefore(last) && consumer.apply(start.getHour(), start.toLocalTime());
             start = start.plusHours(1)) {
        }
    }

    public final static void forEachMinutes(LocalTime begin, LocalTime end, IntBiFunction<LocalTime, Boolean> consumer) {
        LocalDate now = LocalDate.now();
        LocalDateTime last = begin.isBefore(end) ? LocalDateTime.of(now, end) : LocalDateTime.of(now.plusDays(1), end);
        for (LocalDateTime start = LocalDateTime.of(now, begin);
             start.isBefore(last) && consumer.apply(start.getMinute(), start.toLocalTime());
             start = start.plusMinutes(1)) {
        }
    }

    public final static void forEachSeconds(LocalTime begin, LocalTime end, IntBiFunction<LocalTime, Boolean> consumer) {
        LocalDate now = LocalDate.now();
        LocalDateTime last = begin.isBefore(end) ? LocalDateTime.of(now, end) : LocalDateTime.of(now.plusDays(1), end);
        for (LocalDateTime start = LocalDateTime.of(now, begin);
             start.isBefore(last) && consumer.apply(start.getSecond(), start.toLocalTime());
             start = start.plusSeconds(1)) {
        }
    }
}

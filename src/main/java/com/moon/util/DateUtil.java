package com.moon.util;

import com.moon.enums.Const;
import com.moon.lang.LongUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class DateUtil {

    public final static int FIELD_YEAR = Calendar.YEAR;
    public final static int FIELD_MONTH = Calendar.MONTH;
    public final static int FIELD_DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
    public final static int FIELD_DAY_OF_WEEK = Calendar.DAY_OF_WEEK;
    public final static int FIELD_DAY = Calendar.DATE;
    public final static int FIELD_HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
    public final static int FIELD_HOUR = Calendar.HOUR;
    public final static int FIELD_MINUTE = Calendar.MINUTE;
    public final static int FIELD_SECOND = Calendar.SECOND;
    public final static int FIELD_MILLISECOND = Calendar.MILLISECOND;

    public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    public final static String yyyy_MM_dd_hh_mm_ss_SSS = "yyyy-MM-dd hh:mm:ss SSS";
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public final static String yyyy_MM_dd_hh_mm = "yyyy-MM-dd hh:mm";
    public final static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
    public final static String yyyy_MM_dd_hh = "yyyy-MM-dd hh";
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy_MM = "yyyy-MM";
    public final static String yyyy = "yyyy";

    private final static int[] PARSE_FIELD_OF_CALENDAR = {
        FIELD_YEAR,
        FIELD_MONTH,
        FIELD_DAY_OF_MONTH,
        FIELD_HOUR_OF_DAY,
        FIELD_MINUTE,
        FIELD_SECOND,
        FIELD_MILLISECOND
    };

    private DateUtil() {
        noInstanceError();
    }

    public final static long now() {
        return System.currentTimeMillis();
    }

    /*
     * -------------------------------------------------------------------------
     * parsers
     * -------------------------------------------------------------------------
     */

    /**
     * 解析成 Date 日期
     *
     * @param arguments 用 int 表示的年、月、日、时、分、秒、毫秒等一个或多个字段按顺序传入
     */
    public static Date parseToDate(int... arguments) {
        return toDate(parseToCalendar(arguments));
    }

    /**
     * 解析成 Calendar 日期
     *
     * @param arguments 用 int 表示的年、月、日、时、分、秒、毫秒等一个或多个字段按顺序传入
     */
    public static Calendar parseToCalendar(int... arguments) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        int size = arguments.length;
        int length = PARSE_FIELD_OF_CALENDAR.length;

        int i = 0;
        size = size > length ? length : size;
        for (; i < size; i++) {
            int currField = PARSE_FIELD_OF_CALENDAR[i];
            if (currField == FIELD_MONTH) {
                calendar.set(currField, arguments[i] - 1);
            } else {
                calendar.set(currField, arguments[i]);
            }
        }
        for (; i < length; i++) {
            int currField = PARSE_FIELD_OF_CALENDAR[i];
            if (currField == FIELD_DAY_OF_MONTH) {
                calendar.set(currField, 1);
            } else {
                calendar.set(currField, 0);
            }
        }
        return calendar;
    }

    /**
     * 解析成 Date 日期
     *
     * @param arguments 用 String 表示的年、月、日、时、分、秒、毫秒等一个或多个字段按顺序传入
     */
    public static Date parseToDate(String... arguments) {
        return toDate(parseToCalendar(arguments));
    }

    /**
     * 解析成 Calendar 日期
     *
     * @param arguments 用 String 表示的年、月、日、时、分、秒、毫秒等一个或多个字段按顺序传入
     */
    public static Calendar parseToCalendar(String... arguments) {
        int size = arguments.length;
        int length = PARSE_FIELD_OF_CALENDAR.length;

        size = size > length ? length : size;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        int i = 0;
        for (; i < size; i++) {
            int currField = PARSE_FIELD_OF_CALENDAR[i];
            if (currField == FIELD_MONTH) {
                calendar.set(currField, Integer.parseInt(arguments[i]) - 1);
            } else {
                calendar.set(currField, Integer.parseInt(arguments[i]));
            }
        }
        for (; i < length; i++) {
            int currField = PARSE_FIELD_OF_CALENDAR[i];
            if (currField == FIELD_DAY_OF_MONTH) {
                calendar.set(currField, 1);
            } else {
                calendar.set(currField, 0);
            }
        }
        return calendar;
    }

    /**
     * 解析成 Date 日期
     *
     * @param dateString 要求符合格式 "yyyy-MM-dd HH:mm:ss SSS" 的一个或多个字段（超出部分将忽略）
     */
    public static Date parseToDate(String dateString) {
        return toDate(parseToCalendar(dateString));
    }

    /**
     * 解析成 Calendar 日期
     *
     * @param dateString 要求符合格式 "yyyy-MM-dd HH:mm:ss SSS" 的一个或多个字段（超出部分将忽略）
     */
    public static Calendar parseToCalendar(String dateString) {
        dateString = dateString == null ? Const.EMPTY : dateString.trim();
        int strLen = dateString.length();
        int idx = 0;
        if (strLen > idx) {

            List<String> fieldsValue = new ArrayList();
            StringBuilder sb = new StringBuilder();
            char ch;
            boolean moreBlank = false;

            do {
                ch = dateString.charAt(idx++);
                if (ch > 47 && ch < 58) {
                    sb.append(ch);
                    moreBlank = false;
                } else if (!moreBlank) {
                    fieldsValue.add(sb.toString());
                    sb.setLength(0);
                    moreBlank = true;
                }
            } while (strLen > idx);

            if (sb.length() > 0) {
                fieldsValue.add(sb.toString());
            }

            int size = fieldsValue.size();
            if (size == 0) {
                throw new IllegalArgumentException("Must input date string.");
            }
            int length = PARSE_FIELD_OF_CALENDAR.length;

            length = size > length ? length : size;
            Calendar calendar = Calendar.getInstance();
            calendar.clear();

            for (int i = 0; i < length; i++) {
                int currField = PARSE_FIELD_OF_CALENDAR[i];
                if (currField == FIELD_MONTH) {
                    calendar.set(currField, Integer.parseInt(fieldsValue.get(i)) - 1);
                } else {
                    calendar.set(currField, Integer.parseInt(fieldsValue.get(i)));
                }
            }

            return calendar;
        }

        return null;
    }

    public static Date parse(String dateString, String patten) {
        return parse(dateString, new SimpleDateFormat(patten));
    }

    public static Date parse(String dateString, DateFormat patten) {
        try {
            return patten.parse(dateString);
        } catch (ParseException | NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /*
     * -------------------------------------------------------------------------
     * converters
     * -------------------------------------------------------------------------
     */

    public static Time toTime(Object value) {
        if (value == null || value instanceof Time) {
            return (Time) value;
        }
        if (value instanceof Date) {
            return new Time(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new Time(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof CharSequence) {
            String temp = value.toString();
            if (DetectUtil.isNumeric(temp)) {
                return new Time(LongUtil.toLong(temp));
            }
            Calendar c = DateUtil.parseToCalendar(value.toString());
            return new Time(c.getTimeInMillis());
        }
        if (value instanceof Number) {
            long milliseconds = ((Number) value).longValue();
            return new Time(milliseconds);
        }
        throw new IllegalArgumentException("can not converter value to java.sql.Time");
    }

    public static Timestamp toTimestamp(Object value) {
        if (value == null || value instanceof Timestamp) {
            return (Timestamp) value;
        }
        if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new Timestamp(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof CharSequence) {
            String temp = value.toString();
            if (DetectUtil.isNumeric(temp)) {
                return new Timestamp(LongUtil.toLong(temp));
            }
            Calendar c = DateUtil.parseToCalendar(value.toString());
            return new Timestamp(c.getTimeInMillis());
        }
        if (value instanceof Number) {
            long milliseconds = ((Number) value).longValue();
            return new Timestamp(milliseconds);
        }
        throw new IllegalArgumentException("can not converter value to java.sql.Time");
    }

    public static java.sql.Date toSqlDate(Object value) {
        if (value == null || value instanceof Timestamp) {
            return (java.sql.Date) value;
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof CharSequence) {
            String temp = value.toString();
            if (DetectUtil.isNumeric(temp)) {
                return new java.sql.Date(LongUtil.toLong(temp));
            }
            Calendar c = DateUtil.parseToCalendar(value.toString());
            return new java.sql.Date(c.getTimeInMillis());
        }
        if (value instanceof Number) {
            long milliseconds = ((Number) value).longValue();
            return new java.sql.Date(milliseconds);
        }
        throw new IllegalArgumentException("can not converter value to java.sql.Time");
    }

    public static Date toDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        if (value instanceof CharSequence) {
            String temp = value.toString();
            if (DetectUtil.isNumeric(temp)) {
                return new Date(LongUtil.toLong(temp));
            }
            return DateUtil.parseToDate(value.toString());
        }
        if (value instanceof Number) {
            long milliseconds = ((Number) value).longValue();
            return new Date(milliseconds);
        }
        throw new IllegalArgumentException("can not converter value to java.util.Date");
    }

    public static Calendar toCalendar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value);
        }
        if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            return calendar;
        }
        if (value instanceof CharSequence) {
            String temp = value.toString();
            if (DetectUtil.isNumeric(temp)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(LongUtil.toLong(temp));
                return calendar;
            }
            return parseToCalendar(value.toString());
        }
        if (value instanceof Number) {
            long milliseconds = ((Number) value).longValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            return calendar;
        }
        throw new IllegalArgumentException("can not converter value to java.util.Date");
    }
}

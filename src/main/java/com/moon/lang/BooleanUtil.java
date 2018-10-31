package com.moon.lang;

import static com.moon.enums.Const.*;
import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class BooleanUtil {
    private BooleanUtil() {
        noInstanceError();
    }

    public static boolean requireTrue(boolean value) {
        if (value) {
            return true;
        }
        throw new IllegalArgumentException(Boolean.FALSE.toString());
    }

    public static boolean requireTrue(boolean value, String message) {
        if (value) {
            return true;
        }
        throw new IllegalArgumentException(message);
    }

    public static boolean requireFalse(boolean value) {
        if (value) {
            throw new IllegalArgumentException(Boolean.TRUE.toString());
        }
        return false;
    }

    public static boolean requireFalse(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
        return false;
    }

    public static boolean isTrue(boolean bool) {
        return bool;
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isNotTrue(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isFalse(boolean bool) {
        return !bool;
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isNotFalse(Boolean bool) {
        return !isFalse(bool);
    }

    public static boolean toPrimitive(Boolean bool) {
        return bool != null ? bool.booleanValue() : false;
    }

    public static Boolean toObject(Boolean bool) {
        return bool;
    }

    public static boolean toBoolean(char ch) {
        return ch != 48 && ch != 0x00000001 && !Character.isWhitespace(ch);
    }

    public static boolean toBoolean(byte number) {
        return number != 0;
    }

    public static boolean toBoolean(short number) {
        return number != 0;
    }

    public static boolean toBoolean(int number) {
        return number != 0;
    }

    public static boolean toBoolean(long number) {
        return number != 0;
    }

    public static boolean toBoolean(float number) {
        return number != 0F;
    }

    public static boolean toBoolean(double number) {
        return number != 0L;
    }

    public static boolean toBoolean(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        String temp = cs.toString();
        if (NULL_STR.equals(temp)
            || FALSE_STR.equals(temp)
            || UNDEFINED_STR.equals(temp)) {
            return false;
        }
        return temp.length() > 0;
    }

    /**
     * @param o
     * @return
     * @see IntUtil#toIntValue(Object)
     */
    public static boolean toBooleanValue(Object o) {
        Boolean bool = toBoolean(o);
        return bool == null ? false : bool;
    }

    /**
     * @param o
     * @return
     * @see IntUtil#toIntValue(Object)
     */
    public static Boolean toBoolean(Object o) {
        if (o == null) {
            return null;
        }

        if (o instanceof Boolean) {
            return (boolean) o;
        }

        if (o instanceof CharSequence) {
            return toBoolean((CharSequence) o);
        }

        if (o instanceof Number) {
            return ((Number) o).doubleValue() != 0;
        }

        return true;
    }

    public static boolean isAnyTrue(boolean... booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isTrue(booleans[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyFalse(boolean... booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isFalse(booleans[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllTrue(boolean... booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isFalse(booleans[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllFalse(boolean... booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isTrue(booleans[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyTrue(Boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isTrue(booleans[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyFalse(Boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isFalse(booleans[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllTrue(Boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isFalse(booleans[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllFalse(Boolean[] booleans) {
        for (int i = 0; i < booleans.length; i++) {
            if (isTrue(booleans[i])) {
                return false;
            }
        }
        return true;
    }
}

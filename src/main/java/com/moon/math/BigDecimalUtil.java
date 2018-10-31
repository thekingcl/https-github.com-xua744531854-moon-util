package com.moon.math;

import com.moon.lang.SupportUtil;
import com.moon.lang.ThrowUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.moon.lang.ThrowUtil.wrapAndThrow;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class BigDecimalUtil {
    private BigDecimalUtil() {
        ThrowUtil.noInstanceError();
    }

    public static BigDecimal valueOf(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal valueOf(int value) {
        return value == 0 ? BigDecimal.ZERO : (value == 1 ? BigDecimal.ONE : BigDecimal.valueOf(value));
    }

    public static BigDecimal valueOf(long value) {
        return value == 0 ? BigDecimal.ZERO : (value == 1 ? BigDecimal.ONE : BigDecimal.valueOf(value));
    }

    public static BigDecimal valueOf(double value) {
        return value == 0 ? BigDecimal.ZERO : (value == 1 ? BigDecimal.ONE : BigDecimal.valueOf(value));
    }

    public static BigDecimal defaultIfInvalid(String numeric, BigDecimal defaultValue) {
        try {
            return valueOf(numeric);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public static BigDecimal nullIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, null);
    }

    public static BigDecimal zeroIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, BigDecimal.ZERO);
    }

    public static BigDecimal oneIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, BigDecimal.ONE);
    }

    public static BigDecimal zeroIfNull(BigDecimal number) {
        return number == null ? BigDecimal.ZERO : number;
    }

    public static BigDecimal oneIfNull(BigDecimal number) {
        return number == null ? BigDecimal.ONE : number;
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof BigInteger) {
            return new BigDecimal(value.toString());
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof CharSequence) {
            return valueOf(value.toString());
        }
        try {
            return toBigDecimal(SupportUtil.onlyOneItemOrSize(value));
        } catch (Exception e) {
            return wrapAndThrow(e, String.format("Can not cast to BigDecimal of: %s", value));
        }
    }
}

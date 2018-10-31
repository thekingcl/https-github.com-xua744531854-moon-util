package com.moon.math;

import com.moon.lang.SupportUtil;
import com.moon.lang.ThrowUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class BigIntegerUtil {
    private BigIntegerUtil() {
        ThrowUtil.noInstanceError();
    }

    public static BigInteger valueOf(String value) {
        return new BigInteger(value);
    }

    public static BigInteger valueOf(int value) {
        return value == 0 ? BigInteger.ZERO : (value == 1 ? BigInteger.ONE : BigInteger.valueOf(value));
    }

    public static BigInteger valueOf(long value) {
        return value == 0 ? BigInteger.ZERO : (value == 1 ? BigInteger.ONE : BigInteger.valueOf(value));
    }

    public static BigInteger defaultIfInvalid(String numeric, BigInteger defaultValue) {
        try {
            return new BigInteger(numeric);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public static BigInteger nullIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, null);
    }

    public static BigInteger zeroIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, BigInteger.ZERO);
    }

    public static BigInteger oneIfInvalid(String numeric) {
        return defaultIfInvalid(numeric, BigInteger.ONE);
    }

    public static BigInteger zeroIfNull(BigInteger number) {
        return number == null ? BigInteger.ZERO : number;
    }

    public static BigInteger oneIfNull(BigInteger number) {
        return number == null ? BigInteger.ONE : number;
    }

    public static BigInteger toBigInteger(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toBigInteger();
        } else if (value instanceof Number) {
            return BigInteger.valueOf(((Number) value).longValue());
        } else if (value instanceof CharSequence) {
            return new BigInteger(value.toString());
        }
        try {
            Object firstItem = SupportUtil.onlyOneItemOrSize(value);
            return toBigInteger(firstItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Can not cast to BigInteger of: %s", value), e);
        }
    }
}

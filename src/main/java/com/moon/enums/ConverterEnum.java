package com.moon.enums;

import com.moon.lang.*;
import com.moon.math.BigDecimalUtil;
import com.moon.math.BigIntegerUtil;
import com.moon.util.DateUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public enum ConverterEnum implements
    BiFunction<Object, Class, Object> {

    toBooleanValue(boolean.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return BooleanUtil.toBooleanValue(o);
        }
    },
    toBoolean(Boolean.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return BooleanUtil.toBoolean(o);
        }
    },
    toCharValue(char.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return CharUtil.toCharValue(o);
        }
    },
    toCharacter(Character.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return CharacterUtil.toCharacter(o);
        }
    },
    toByteValue(byte.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return ByteUtil.toByteValue(o);
        }
    },
    toByte(Byte.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return ByteUtil.toByte(o);
        }
    },
    toShortValue(short.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return ShortUtil.toShortValue(o);
        }
    },
    toShort(Short.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return ShortUtil.toShort(o);
        }
    },
    toIntValue(int.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return IntUtil.toIntValue(o);
        }
    },
    toInteger(Integer.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return IntegerUtil.toInteger(o);
        }
    },
    toLongValue(long.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return LongUtil.toLongValue(o);
        }
    },
    toLong(Long.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return LongUtil.toLong(o);
        }
    },
    toFloatValue(float.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return FloatUtil.toFloatValue(o);
        }
    },
    toFloat(Float.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return FloatUtil.toFloat(o);
        }
    },
    toDoubleValue(double.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DoubleUtil.toDoubleValue(o);
        }
    },
    toDouble(Double.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DoubleUtil.toDouble(o);
        }
    },
    toBigInteger(BigInteger .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return BigIntegerUtil.toBigInteger(o);
        }
    },
    toBigDecimal(BigDecimal .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return BigDecimalUtil.toBigDecimal(o);
        }
    },

    toString(String.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return StringUtil.toString(o);
        }
    },
    toStringBuffer(StringBuffer.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return new StringBuffer(String.valueOf(o));
        }
    },
    toStringBuilder(StringBuilder.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return new StringBuilder(String.valueOf(o));
        }
    },
    toDate(Date .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DateUtil.toDate(o);
        }
    },
    toTime(Time .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DateUtil.toTime(o);
        }
    },
    toCalendar(Calendar .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DateUtil.toCalendar(o);
        }
    },
    toTimestamp(Timestamp .class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DateUtil.toTimestamp(o);
        }
    },
    toSqlDate(java.sql.Date.class) {
        @Override
        public Object apply(Object o, Class aClass) {
            return DateUtil.toSqlDate(o);
        }
    },
    toEnum(Enum.class) {
        @Override
        public Object apply(Object o, Class type) {
            return EnumUtil.toEnum(o, type);
        }
    },;

    private final Class type;

    private static class Cached {
        final static Map<Class, ConverterEnum> CACHE = new HashMap();
    }

    ConverterEnum(Class type) {
        Cached.CACHE.put(this.type = type, this);
    }

    public Class type() {
        return type;
    }

    public static <T> T to(Object o, Class type) {
        ConverterEnum converter = Objects.requireNonNull(Cached.CACHE.get(type));
        return (T) converter.apply(o, type);
    }
}

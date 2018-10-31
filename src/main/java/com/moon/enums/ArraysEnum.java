package com.moon.enums;

import com.moon.util.able.IteratorAble;
import com.moon.util.able.StringifyAble;
import com.moon.util.function.IntBiFunction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

import static com.moon.enums.Const.EMPTY;
import static com.moon.lang.JoinerUtil.join;
import static com.moon.util.IteratorUtil.of;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public enum ArraysEnum implements StringifyAble, IteratorAble, IntBiFunction, Predicate, To {
    OBJECTS(new Object[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public Object[] to(Object o) {
            return (Object[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof Object[];
        }
    },
    BOOLEANS(new boolean[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public boolean[] to(Object o) {
            return (boolean[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof boolean[];
        }
    },
    DOUBLES(new double[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public double[] to(Object o) {
            return (double[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof double[];
        }
    },
    FLOATS(new float[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public float[] to(Object o) {
            return (float[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof float[];
        }
    },
    LONGS(new long[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public long[] to(Object o) {
            return (long[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof long[];
        }
    },
    INTS(new int[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public int[] to(Object o) {
            return (int[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof int[];
        }
    },
    SHORTS(new short[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public short[] to(Object o) {
            return (short[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof short[];
        }
    },
    BYTES(new byte[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public byte[] to(Object o) {
            return (byte[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            return join(to(o), EMPTY);
        }

        @Override
        public boolean test(Object data) {
            return data instanceof byte[];
        }
    },
    CHARS(new char[0]) {
        @Override
        public Object apply(int value, Object obj) {
            return to(obj)[value];
        }

        @Override
        public char[] to(Object o) {
            return (char[]) o;
        }

        @Override
        public Iterator iterator(Object o) {
            return of(to(o));
        }

        @Override
        public String stringify(Object o) {
            if (o == null) {
                return null;
            }
            return new String(to(o));
        }

        @Override
        public boolean test(Object data) {
            return data instanceof char[];
        }
    };

    static class Cached {
        final static HashMap<Class, ArraysEnum> CACHE = new HashMap<>();
    }

    private final Class type;
    private final Object empty;

    ArraysEnum(Object empty) {
        Cached.CACHE.putIfAbsent(this.type = (this.empty = empty).getClass(), this);
    }

    public Class type() {
        return type;
    }

    public <T> T empty() {
        return (T) empty;
    }

    public static ArraysEnum get(Class type) {
        return Cached.CACHE.get(type);
    }

    public static ArraysEnum getOrDefault(Class type, ArraysEnum defaultVal) {
        return Cached.CACHE.getOrDefault(type, defaultVal);
    }

    public static ArraysEnum getOrObjects(Class type) {
        return getOrDefault(type, OBJECTS);
    }
}

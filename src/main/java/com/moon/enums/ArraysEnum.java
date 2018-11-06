package com.moon.enums;

import com.moon.lang.BooleanUtil;
import com.moon.util.IteratorUtil;
import com.moon.util.function.IntBiConsumer;

import java.util.HashMap;
import java.util.Iterator;

import static com.moon.enums.Const.EMPTY;
import static com.moon.lang.JoinerUtil.join;
import static com.moon.util.IteratorUtil.of;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public enum ArraysEnum implements ArrayOperators {
    OBJECTS(new Object[0]) {
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

        @Override
        public Object get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public Object[] create(int length) {
            return new Object[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            IteratorUtil.forEach(to(arr), consumer);
        }
    },
    BOOLEANS(new boolean[0]) {
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

        @Override
        public Boolean get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public boolean[] create(int length) {
            return new boolean[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            boolean[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    DOUBLES(new double[0]) {
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

        @Override
        public Double get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public double[] create(int length) {
            return new double[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            double[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    FLOATS(new float[0]) {
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

        @Override
        public Float get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public float[] create(int length) {
            return new float[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            float[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    LONGS(new long[0]) {
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

        @Override
        public Long get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public long[] create(int length) {
            return new long[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            long[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    INTS(new int[0]) {
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

        @Override
        public Integer get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public int[] create(int length) {
            return new int[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            int[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    SHORTS(new short[0]) {
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

        @Override
        public Short get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public short[] create(int length) {
            return new short[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            short[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    BYTES(new byte[0]) {
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

        @Override
        public Byte get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public byte[] create(int length) {
            return new byte[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            byte[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    },
    CHARS(new char[0]) {
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
            return o == null ? null : new String(to(o));
        }

        @Override
        public boolean test(Object data) {
            return data instanceof char[];
        }

        @Override
        public Character get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public char[] create(int length) {
            return new char[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            char[] array = to(arr);
            for (int i = 0, length = array.length; i < length; i++) {
                consumer.accept(array[i], i);
            }
        }
    };

    static class Cached {
        final static HashMap<Class, ArrayOperators> CACHE = new HashMap<>();
    }

    public final Class TYPE;
    private final Object empty;

    ArraysEnum(Object empty) {
        Cached.CACHE.put(this.TYPE = (this.empty = empty).getClass(), this);
    }

    @Override
    public <T> T empty() {
        return (T) empty;
    }

    public static ArrayOperators get(Class type) {
        return Cached.CACHE.get(type);
    }

    public static ArrayOperators getOrDefault(Class type, ArrayOperators defaultVal) {
        BooleanUtil.requireTrue(type.isArray());
        return Cached.CACHE.getOrDefault(type, defaultVal);
    }

    public static ArrayOperators getOrObjects(Class type) {
        return getOrDefault(type, OBJECTS);
    }

    public static ArrayOperators getOrDefault(Object array, ArrayOperators defaultType) {
        return getOrDefault(array.getClass(), defaultType);
    }

    public static ArrayOperators getOrObjects(Object array) {
        return getOrObjects(array.getClass());
    }
}

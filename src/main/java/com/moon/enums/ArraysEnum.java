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
        public Object set(Object arr, int index, Object value) {
            Object[] data = to(arr);
            Object old = data[index];
            data[index] = value;
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null) {
                return false;
            }
            Object[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            if (item == null) {
                for (int i = 0; i < len; i++) {
                    if (data[i] == null) {
                        return true;
                    }
                }
            }
            for (int i = 0; i < len; i++) {
                if (item.equals(data[i])) {
                    return true;
                }
            }
            return false;
        }
    },
    STRINGS(new String[0]) {
        @Override
        public String[] to(Object o) {
            return (String[]) o;
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
            return data instanceof String[];
        }

        @Override
        public Object get(Object arr, int index) {
            return to(arr)[index];
        }

        @Override
        public String set(Object arr, int index, Object value) {
            String[] data = to(arr);
            String old = data[index];
            data[index] = value == null ? null : value.toString();
            return old;
        }

        @Override
        public String[] create(int length) {
            return new String[length];
        }

        @Override
        public int length(Object arr) {
            return to(arr).length;
        }

        @Override
        public void forEach(Object arr, IntBiConsumer consumer) {
            IteratorUtil.forEach(to(arr), consumer);
        }

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || !(item instanceof String)) {
                return false;
            }
            String[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            if (item == null) {
                for (int i = 0; i < len; i++) {
                    if (data[i] == null) {
                        return true;
                    }
                }
            }
            for (int i = 0; i < len; i++) {
                if (item.equals(data[i])) {
                    return true;
                }
            }
            return false;
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
        public Boolean set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Boolean);
            boolean[] data = to(arr);
            boolean old = data[index];
            data[index] = ((Boolean) value).booleanValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Boolean)) {
                return false;
            }
            boolean[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            if (((Boolean) item).booleanValue()) {
                for (int i = 0; i < len; i++) {
                    if (data[i]) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < len; i++) {
                    if (!data[i]) {
                        return true;
                    }
                }
            }
            return false;
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
        public Double set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            double[] data = to(arr);
            double old = data[index];
            data[index] = ((Number) value).doubleValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Double)) {
                return false;
            }
            double[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            double value = ((Double) item).doubleValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Float set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            float[] data = to(arr);
            float old = data[index];
            data[index] = ((Number) value).floatValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Float)) {
                return false;
            }
            float[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            float value = ((Float) item).floatValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Long set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            long[] data = to(arr);
            long old = data[index];
            data[index] = ((Number) value).longValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Long)) {
                return false;
            }
            long[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            long value = ((Long) item).longValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Integer set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            int[] data = to(arr);
            int old = data[index];
            data[index] = ((Number) value).intValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Integer)) {
                return false;
            }
            int[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            int value = ((Integer) item).intValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Short set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            short[] data = to(arr);
            short old = data[index];
            data[index] = ((Number) value).shortValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Short)) {
                return false;
            }
            short[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            int value = ((Short) item).shortValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Byte set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Number);
            byte[] data = to(arr);
            byte old = data[index];
            data[index] = ((Number) value).byteValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Byte)) {
                return false;
            }
            byte[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            int value = ((Byte) item).byteValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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
        public Character set(Object arr, int index, Object value) {
            BooleanUtil.requireTrue(value instanceof Character);
            char[] data = to(arr);
            char old = data[index];
            data[index] = ((Character) value).charValue();
            return old;
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

        @Override
        public boolean contains(Object arr, Object item) {
            if (arr == null || item == null || !(item instanceof Character)) {
                return false;
            }
            char[] data = to(arr);
            int len = data.length;
            if (len == 0) {
                return false;
            }
            char value = ((Character) item).charValue();
            for (int i = 0; i < len; i++) {
                if (data[i] == value) {
                    return true;
                }
            }
            return false;
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

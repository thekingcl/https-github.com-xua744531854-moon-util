package com.moon.util.compute.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author benshaoye
 */
final class DataGetterOpposite implements AsGetter {
    final AsHandler handler;

    DataGetterOpposite(AsHandler handler) {
        this.handler = handler;
    }

    private static Map<Class, Function<Object, Number>> CACHE;

    static {
        CACHE = new HashMap<>();
        CACHE.put(Integer.TYPE, (Function) num -> -((Number) num).intValue());
        CACHE.put(Long.TYPE, (Function) num -> -((Number) num).longValue());
        CACHE.put(Double.TYPE, (Function) num -> -((Number) num).doubleValue());
        CACHE.put(Float.TYPE, (Function) num -> -((Number) num).floatValue());
        CACHE.put(Short.TYPE, (Function) num -> -((Number) num).shortValue());
        CACHE.put(Byte.TYPE, (Function) num -> -((Number) num).byteValue());
        CACHE = null;
    }

    @Override
    public Object use(Object data) {
        Object value = handler.use(data);
        if (value instanceof Integer) {
            return -((Integer) value).intValue();
        } else if (value instanceof Double || value instanceof Float) {
            return -((Number) value).doubleValue();
        } else if (value instanceof Long) {
            return -((Number) value).longValue();
        } else if (value instanceof Number) {
            return -((Number) value).intValue();
        }
        throw new IllegalArgumentException(toString());
    }

    @Override
    public String toString() {
        return "-" + handler.toString();
    }
}

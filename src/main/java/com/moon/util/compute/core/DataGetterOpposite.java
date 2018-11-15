package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
final class DataGetterOpposite implements AsGetter {
    final AsRunner handler;

    DataGetterOpposite(AsRunner handler) {
        this.handler = handler;
    }

    @Override
    public Object run(Object data) {
        Object value = handler.run(data);
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

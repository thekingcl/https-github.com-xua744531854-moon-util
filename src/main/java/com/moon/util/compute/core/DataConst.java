package com.moon.util.compute.core;

import com.moon.lang.BooleanUtil;
import com.moon.lang.ref.ReferenceUtil;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author benshaoye
 */
abstract class DataConst<T> implements AsConst {

    final static AsConst NULL = DataConstNull.NULL;
    final static AsConst TRUE = DataConstBoolean.TRUE;
    final static AsConst FALSE = DataConstBoolean.FALSE;

    private final static Map<Object, AsConst> CACHE = ReferenceUtil.manageMap();
    private final static ReentrantLock LOCK = new ReentrantLock();

    protected final static AsConst getValue(Object key) {
        return CACHE.get(key);
    }

    protected final static AsConst putValue(Object key, AsConst value) {
        try {
            LOCK.lock();
            CACHE.put(key, value);
            return value;
        } finally {
            LOCK.unlock();
        }
    }

    final T value;

    protected DataConst(T value) {
        this.value = value;
    }

    @Override
    public Object run(Object data) {
        return value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean isNumber() {
        return value instanceof Number;
    }

    @Override
    public boolean isString() {
        return value instanceof CharSequence;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static final AsConst get(Object data) {
        if (data == null) {
            return DataConst.NULL;
        }
        if (data instanceof CharSequence) {
            return DataConstString.valueOf(data.toString());
        }
        if (data instanceof Number) {
            return DataConstNumber.valueOf((Number) data);
        }
        if (Boolean.TRUE.equals(data)) {
            return DataConst.TRUE;
        }
        if (Boolean.FALSE.equals(data)) {
            return DataConst.FALSE;
        }
        if (data instanceof AsConst) {
            return (AsConst) data;
        }
        return DataConstObj.valueOf(data);
    }

    public static final AsConst temp(Object data){
        if (data == null) {
            return DataConst.NULL;
        }
        if (data instanceof CharSequence) {
            return DataConstString.tempStr(data.toString());
        }
        if (data instanceof Number) {
            return DataConstNumber.tempNum((Number) data);
        }
        if (Boolean.TRUE.equals(data)) {
            return DataConst.TRUE;
        }
        if (Boolean.FALSE.equals(data)) {
            return DataConst.FALSE;
        }
        if (data instanceof AsConst) {
            return (AsConst) data;
        }
        return DataConstObj.tempObj(data);
    }

    public static final AsConst getOpposite(DataConst data) {
        BooleanUtil.requireTrue(data instanceof DataConstNumber);
        Number num = (Number) data.run(), value;
        if (num instanceof Double || num instanceof Float) {
            value = -num.doubleValue();
        } else if (num instanceof Long) {
            value = -num.longValue();
        } else {
            value = -num.intValue();
        }
        return DataConstNumber.valueOf(value);
    }
}

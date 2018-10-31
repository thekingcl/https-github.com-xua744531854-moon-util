package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
class DataConstObj extends DataConst {
    private DataConstObj(Object value) {
        super(value);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    final static AsConst valueOf(Object str) {
        AsConst CONST = CACHE.get(str);
        if (CONST == null) {
            CONST = new DataConstObj(str);
            CACHE.put(str, CONST);
        }
        return CONST;
    }
}

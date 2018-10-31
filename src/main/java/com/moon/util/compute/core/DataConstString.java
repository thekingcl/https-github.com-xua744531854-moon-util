package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
class DataConstString extends DataConst {
    private DataConstString(Object value) {
        super(value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    final static AsConst valueOf(String str) {
        AsConst CONST = CACHE.get(str);
        if (CONST == null) {
            CONST = new DataConstString(str);
            CACHE.put(str, CONST);
        }
        return CONST;
    }
}

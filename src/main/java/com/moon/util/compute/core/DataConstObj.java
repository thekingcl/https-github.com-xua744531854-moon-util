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
        AsConst CONST = getValue(str);
        if (CONST == null) {
            CONST = putValue(str, new DataConstObj(str));
        }
        return CONST;
    }

    final static AsConst tempObj(Object str) {
        return new DataConstObj(str);
    }
}

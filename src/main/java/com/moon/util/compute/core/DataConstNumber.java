package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
class DataConstNumber extends DataConst {
    private DataConstNumber(Number value) {
        super(value);
    }

    @Override
    public boolean isNumber() {
        return true;
    }


    final static AsConst valueOf(Number str) {
        AsConst CONST = CACHE.get(str);
        if (CONST == null) {
            CONST = new DataConstNumber(str);
            CACHE.put(str, CONST);
        }
        return CONST;
    }
}

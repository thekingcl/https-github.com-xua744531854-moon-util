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
        AsConst CONST = getValue(str);
        if (CONST == null) {
            CONST = putValue(str, new DataConstNumber(str));
        }
        return CONST;
    }

    final static AsConst tempNum(Number str) {
        return new DataConstNumber(str);
    }
}

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
        AsConst CONST = getValue(str);
        if (CONST == null) {
            CONST = putValue(str, new DataConstString(str));
        }
        return CONST;
    }

    final static AsConst tempStr(String str) {
        return new DataConstString(str);
    }
}

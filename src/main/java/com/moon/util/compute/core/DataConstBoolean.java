package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
enum DataConstBoolean implements AsConst {
    TRUE {
        @Override
        public Object run(Object data) {
            return Boolean.TRUE;
        }
    },
    FALSE {
        @Override
        public Object run(Object data) {
            return Boolean.FALSE;
        }
    };

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

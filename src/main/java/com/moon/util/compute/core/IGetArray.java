package com.moon.util.compute.core;

import com.moon.enums.ArraysEnum;

/**
 * @author benshaoye
 */
class IGetArray implements IGetter {
    ArraysEnum getter;

    public boolean sourceTest(Object data) {
        return data.getClass().isArray();
    }

    /**
     * Applies this function to the given arguments.
     *
     * @param o  the first function argument
     * @param o2 the second function argument
     * @return the function result
     */
    @Override
    public Object apply(Object o, Object o2) {
        return getter == null || !test(o)
            ? reset(o).apply(((Number) o2).intValue(), o)
            : getter.apply(((Number) o2).intValue(), o);
    }

    ArraysEnum reset(Object data) {
        getter = ArraysEnum.getOrObjects(data.getClass());
        return getter;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param o the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Object o) {
        return getter == null ? sourceTest(o) : getter.test(o) || sourceTest(o);
    }
}

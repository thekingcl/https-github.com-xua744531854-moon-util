package com.moon.util.compute.core;

/**
 * 从前一个数据获取值
 *
 * @author benshaoye
 */
class DataGetterHandle implements AsGetter {
    final AsHandler handler;

    IGetter getter;

    DataGetterHandle(AsHandler handler) {
        this.handler = handler;
    }

    @Override
    public Object use(Object data) {
        Object key = handler.use(data);

        IGetter getter = this.getter;
        if (getter == null || !getter.test(data)) {
            this.getter = getter = IGetter.reset(data, key);
        }
        return getter.apply(data, key);
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
        return getter != null && getter.test(o);
    }
}

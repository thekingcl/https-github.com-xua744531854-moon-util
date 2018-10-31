package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
class DataGetterComplex implements AsGetter {

    final AsValuer beforeItem;
    final AsValuer afterItem;

    IGetter getter;

    DataGetterComplex(AsValuer beforeItem, AsValuer afterItem) {
        this.beforeItem = beforeItem;
        this.afterItem = afterItem;
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
        return true;
    }

    @Override
    public boolean isGetterComplex() {
        return true;
    }

    @Override
    public Object use(Object data) {
        Object prevData = beforeItem.use(data);
        Object afterData = afterItem.use(data);
        IGetter getter = this.getter;
        if (getter == null) {
            this.getter = getter = IGetter.reset(prevData, afterData);
        }
        return getter.apply(prevData, afterData);
    }
}

package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
final class DataGetterNot implements AsGetter {

    final AsRunner valuer;

    DataGetterNot(AsRunner valuer) {
        this.valuer = valuer;
    }

    @Override
    public Object run(Object data) {
        return !((Boolean) valuer.run(data)).booleanValue();
    }

    /**
     * 这个方法在这儿不能保证数据强正确性
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
    public String toString() {
        return "!" + valuer.toString();
    }
}

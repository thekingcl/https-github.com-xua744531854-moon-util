package com.moon.util.iterators;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
abstract class BaseArrayIterator {

    protected int index;
    protected final int length;

    protected BaseArrayIterator(int length) {
        this.length = length;
    }
}

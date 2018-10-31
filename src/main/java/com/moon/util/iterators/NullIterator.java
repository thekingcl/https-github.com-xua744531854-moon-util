package com.moon.util.iterators;

import java.util.Iterator;

/**
 * @author ZhangDongMin
 * @date 2018/3/9 16:20
 */
public enum NullIterator implements Iterator {

    NULL;

    public static final NullIterator Null = NULL;

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        throw new UnsupportedOperationException();
    }
}

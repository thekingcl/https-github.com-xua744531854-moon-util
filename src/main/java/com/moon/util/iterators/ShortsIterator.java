package com.moon.util.iterators;

import java.util.Iterator;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class ShortsIterator
    extends BaseArrayIterator
    implements Iterator<Short> {

    private short[] array;

    public ShortsIterator(short[] array) {
        super(array == null ? 0 : array.length);
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public Short next() {
        return this.array[index++];
    }
}

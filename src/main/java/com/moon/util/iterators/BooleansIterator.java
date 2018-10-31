package com.moon.util.iterators;

import java.util.Iterator;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class BooleansIterator
    extends BaseArrayIterator
    implements Iterator<Boolean> {

    private boolean[] array;

    public BooleansIterator(boolean[] array) {
        super(array == null ? 0 : array.length);
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public Boolean next() {
        return this.array[index++];
    }
}

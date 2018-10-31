package com.moon.util.iterators;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class EnumerationIterator<T> implements Iterator<T> {

    private Enumeration<T> enumeration;

    public EnumerationIterator(Enumeration<T> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        try {
            return this.enumeration.hasMoreElements();
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public T next() {
        return this.enumeration.nextElement();
    }
}

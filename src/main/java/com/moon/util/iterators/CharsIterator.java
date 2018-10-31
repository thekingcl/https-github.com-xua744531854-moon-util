package com.moon.util.iterators;

import java.util.Iterator;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class CharsIterator
    extends BaseArrayIterator
    implements Iterator<Character> {

    private char[] array;

    public CharsIterator(CharSequence string) {
        super(string == null ? 0 : string.length());
        if (string != null) {
            this.array = string.toString().toCharArray();
        }
    }

    public CharsIterator(char[] array) {
        super(array == null ? 0 : array.length);
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public Character next() {
        return this.array[index++];
    }
}

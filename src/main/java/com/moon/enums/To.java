package com.moon.enums;

/**
 * @author benshaoye
 */
interface To {
    /**
     * 转成一个对象
     *
     * @param o
     * @param <T>
     * @return
     */
    <T> T to(Object o);
}

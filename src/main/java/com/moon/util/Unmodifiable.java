package com.moon.util;


/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
@FunctionalInterface
public interface Unmodifiable<T> {

    Unmodifiable<T> flipToNot();
}

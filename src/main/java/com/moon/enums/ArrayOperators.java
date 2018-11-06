package com.moon.enums;

import com.moon.util.able.IteratorAble;
import com.moon.util.able.Stringify;
import com.moon.util.function.IntBiConsumer;

import java.lang.reflect.Array;
import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public interface ArrayOperators extends IteratorAble, Predicate, Stringify {

    /**
     * 转成一个对象
     *
     * @param o
     * @param <T>
     * @return
     */
    <T> T to(Object o);

    /**
     * 获取空数组对象
     *
     * @param <T>
     * @return
     */
    <T> T empty();

    /**
     * 创建一个指定长度数组
     *
     * @param length
     * @param <T>
     * @return
     */
    <T> T create(int length);

    /**
     * 或者数组指定索引项
     *
     * @param arr
     * @param index
     * @return
     */
    default <T> T get(Object arr, int index) {
        return (T) Array.get(arr, index);
    }

    /**
     * 求数组长度
     *
     * @param arr
     * @return
     */
    default int length(Object arr) {
        return arr == null ? 0 : Array.getLength(arr);
    }

    /**
     * 迭代处理数组每一项
     *
     * @param arr
     * @param consumer
     */
    default void forEach(Object arr, IntBiConsumer consumer) {
        for (int i = 0, len = length(arr); i < len; i++) {
            consumer.accept(get(arr, i), i);
        }
    }
}

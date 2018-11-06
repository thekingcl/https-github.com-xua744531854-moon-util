package com.moon.enums;

import com.moon.util.able.IteratorAble;
import com.moon.util.able.Stringify;
import com.moon.util.function.IntBiConsumer;

import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public interface ArrayOperators extends IteratorAble, Predicate, Stringify {
    /**
     * 或者数组指定索引项
     *
     * @param arr
     * @param index
     * @return
     */
    <T> T get(Object arr, int index);

    /**
     * 创建一个指定长度数组
     *
     * @param length
     * @param <T>
     * @return
     */
    <T> T create(int length);

    /**
     * 转成一个对象
     *
     * @param o
     * @param <T>
     * @return
     */
    <T> T to(Object o);

    /**
     * 求数组长度
     *
     * @param arr
     * @return
     */
    int length(Object arr);

    /**
     * 迭代处理数组每一项
     *
     * @param arr
     * @param consumer
     */
    void forEach(Object arr, IntBiConsumer consumer);

    /**
     * 获取空数组对象
     *
     * @param <T>
     * @return
     */
    <T> T empty();
}

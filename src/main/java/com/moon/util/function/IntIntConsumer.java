package com.moon.util.function;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
@FunctionalInterface
public interface IntIntConsumer {
    /**
     * int array handler
     * @param value current data
     * @param index current get
     */
    void accept(int value, int index);
}

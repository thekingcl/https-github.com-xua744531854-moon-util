package com.moon.util.function;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
@FunctionalInterface
public interface IntLongConsumer {
    /**
     * long array handler
     * @param value current data
     * @param index current get
     */
    void accept(long value, int index);
}

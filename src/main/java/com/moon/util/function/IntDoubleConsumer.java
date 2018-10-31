package com.moon.util.function;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
@FunctionalInterface
public interface IntDoubleConsumer {
    /**
     * double array handler
     * @param value current items
     * @param index current get
     */
    void accept(double value, int index);
}

package com.moon.util.function;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
@FunctionalInterface
public interface IntBiFunction<T, R> {

    R apply(int value, T obj);
}

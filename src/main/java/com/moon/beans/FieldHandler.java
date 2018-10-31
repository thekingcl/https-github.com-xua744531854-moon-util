package com.moon.beans;

import java.lang.reflect.InvocationTargetException;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
@FunctionalInterface
public interface FieldHandler {
    /**
     * 方法执行
     *
     * @param source 源对象
     * @param value  字段值
     * @return 返回字段值，或 setter
     * @throws InvocationTargetException 执行异常
     * @throws IllegalAccessException    访问异常
     */
    Object handle(Object source, Object value) throws
        InvocationTargetException,
        IllegalAccessException;
}

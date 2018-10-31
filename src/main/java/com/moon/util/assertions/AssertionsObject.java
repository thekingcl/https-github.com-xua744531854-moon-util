package com.moon.util.assertions;

import java.util.Objects;

/**
 * @author benshaoye
 */
interface AssertionsObject extends AssertionsFail {

    /**
     * 是否是 null
     *
     * @param value
     * @return
     */
    default boolean assertNull(Object value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value == null, null, value);
    }

    /**
     * 是否不是 null
     *
     * @param value
     * @return
     */
    default boolean assertNotNull(Object value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value != null, "<not null>", value);
    }

    /**
     * value1 和 value2 是否是同一个对象
     *
     * @param value1
     * @param value2
     * @return
     */
    default boolean assertSame(Object value1, Object value2) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            value1 == value2, value1, value2);
    }

    /**
     * value1 和 value2 是否不是同一个对象
     *
     * @param value1
     * @param value2
     * @return
     */
    default boolean assertNotSame(Object value1, Object value2) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            value1 != value2, value1, value2);
    }

    /**
     * 是否相等
     *
     * @param value1
     * @param value2
     * @return
     */
    default boolean assertEquals(Object value1, Object value2) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            Objects.equals(value1, value2), value1, value2);
    }

    /**
     * 是否不相等
     *
     * @param value1
     * @param value2
     * @return
     */
    default boolean assertNotEquals(Object value1, Object value2) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            !Objects.equals(value1, value2), value1, value2);
    }

    /**
     * 是否是指定类型的实例
     *
     * @param value
     * @param expectType
     * @return
     */
    default boolean assertInstanceOf(Object value, Class expectType) {
        return success("Test result: {}\n\tExpect type: {}\n\tActual type: {}",
            expectType.isInstance(value), expectType,
            value == null ? "<null>.class" : value.getClass());
    }

    /**
     * 是否不是指定类型的实例
     *
     * @param value
     * @param expectType
     * @return
     */
    default boolean assertNotInstanceOf(Object value, Class expectType) {
        return success("Test result: {}\n\tExpect not an instance of type: {}\n\tActual type: {}",
            !expectType.isInstance(value), expectType,
            value == null ? "<null>.class" : value.getClass());
    }
}

package com.moon.util.assertions;

/**
 * @author benshaoye
 */
interface AssertionsMath extends AssertionsFail {
    /**
     * 两个数值是否相等
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertEq(long value, long value1) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            value == value1, value1, value);
    }
    /**
     * 两个数值是否相等
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertEq(double value, double value1) {
        return success("Test result: {}\n\tValue1: {}\n\tValue2: {}",
            value == value1, value1, value);
    }

    /**
     * value 是否小于 value1
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertLt(long value, long value1) {
        return success("Test result: {}\n\tExpect: less than {}\n\tActual: {}",
            value < value1, value1, value);
    }

    /**
     * value 是否小于 value1
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertLt(double value, double value1) {
        return success("Test result: {}\n\tExpect: less than {}\n\tActual: {}",
            value < value1, value1, value);
    }

    /**
     * value 是否大于 value1
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertGt(long value, long value1) {
        return success("Test result: {}\n\tExpect: great than {}\n\tActual: {}",
            value > value1, value1, value);
    }

    /**
     * value 是否大于 value1
     *
     * @param value
     * @param value1
     * @return
     */
    default boolean assertGt(double value, double value1) {
        return success("Test result: {}\n\tExpect: great than {}\n\tActual: {}",
            value > value1, value1, value);
    }
}

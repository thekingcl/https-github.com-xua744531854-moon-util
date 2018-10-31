package com.moon.util.assertions;

/**
 * @author benshaoye
 */
interface AssertionsType extends AssertionsFail {
    /**
     * 是否是指定类型实例
     *
     * @param value
     * @param type
     * @return
     */
    default boolean assertType(Object value, Class type) {
        return success("Test result: {}\n\tExpect type: {}\n\tActual type: {}",
            value != null && value.getClass() == type, type,
            value == null ? "<null>.class" : value.getClass());
    }

    /**
     * 是否不是指定类型实例
     *
     * @param value
     * @param type
     * @return
     */
    default boolean assertNotType(Object value, Class type) {
        return success("Test result: {}\n\tExpect type: {}\n\tActual type: {}",
            value != null && value.getClass() == type, type,
            value == null ? "<null>.class" : value.getClass());
    }
}

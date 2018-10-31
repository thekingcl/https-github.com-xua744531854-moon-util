package com.moon.util.assertions;

/**
 * @author benshaoye
 */
interface AssertionsBoolean extends AssertionsFail {
    /**
     * 是否是 true
     *
     * @param value
     * @return
     */
    default boolean assertTrue(boolean value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}", value, true, value);
    }

    /**
     * 是否是 false
     *
     * @param value
     * @return
     */
    default boolean assertFalse(boolean value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}", !value, false, value);
    }
}

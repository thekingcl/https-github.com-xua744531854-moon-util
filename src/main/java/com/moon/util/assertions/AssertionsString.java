package com.moon.util.assertions;

/**
 * @author benshaoye
 */
interface AssertionsString extends AssertionsFail {
    /**
     * 是否是空字符串
     *
     * @param value
     * @return
     */
    default boolean assertEmpty(String value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value == null || value.length() == 0, "<null or \"\">", value);
    }

    /**
     * 是否是空字符串
     *
     * @param value
     * @return
     */
    default boolean assertNotEmpty(String value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value != null && value.length() > 0, "<not null and not an empty string>", value);
    }

    /**
     * 是否是空字符串
     *
     * @param value
     * @return
     */
    default boolean assertBlank(String value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value == null || value.trim().length() == 0, "<null, \"\" or like \" \">", value);
    }

    /**
     * 是否是空字符串
     *
     * @param value
     * @return
     */
    default boolean assertNotBlank(String value) {
        return success("Test result: {}\n\tExpect: {}\n\tActual: {}",
            value != null && value.trim().length() > 0, "<not null and all char isn't blank>", value);
    }
}

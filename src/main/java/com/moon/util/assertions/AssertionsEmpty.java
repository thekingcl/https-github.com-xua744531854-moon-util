package com.moon.util.assertions;

import java.util.Collection;
import java.util.Map;

/**
 * @author benshaoye
 */
interface AssertionsEmpty extends AssertionsFail {
    /**
     * 一个空集合
     *
     * @param collection
     * @return
     */
    default boolean assertEmpty(Collection collection) {
        int size = collection == null ? 0 : collection.size();
        return success("Test result: {}\n\tExpect size: 0 (null or an empty collection)\n\tActual size: {}",
            size == 0, size);
    }

    /**
     * 一个空集合
     *
     * @param collection
     * @return
     */
    default boolean assertEmpty(Map collection) {
        int size = collection == null ? 0 : collection.size();
        return success("Test result: {}\n\tExpect size: 0 (null or an empty map)\n\tActual size: {}",
            size == 0, size);
    }

    /**
     * 不是一个空集合
     *
     * @param collection
     * @return
     */
    default boolean assertNotEmpty(Collection collection) {
        int size = collection == null ? 0 : collection.size();
        return success("Test result: {}\n\tExpect size: great than 0\n\tActual size: {}",
            size > 0, size);
    }

    /**
     * 不是一个空集合
     *
     * @param collection
     * @return
     */
    default boolean assertNotEmpty(Map collection) {
        int size = collection == null ? 0 : collection.size();
        return success("Test result: {}\n\tExpect size: great than 0\n\tActual size: {}",
            size > 0, size);
    }
}

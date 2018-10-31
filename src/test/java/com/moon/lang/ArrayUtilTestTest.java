package com.moon.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class ArrayUtilTestTest {

    @Test
    void testRemoveIndex() {
        String[] arr = {"a", "b", "c", "d", "e", "f"};
        ArrayUtil.removeIndex(arr, 2);
        assertNull(arr[arr.length - 1]);
        assertEquals(arr[2], "d");
    }

    @Test
    void testSplice() {
        boolean[] booleans = {true, true, true, true, true, true};
    }
}
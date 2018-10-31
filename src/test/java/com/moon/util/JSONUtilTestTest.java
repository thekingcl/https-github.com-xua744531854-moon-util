package com.moon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class JSONUtilTestTest {

    @Test
    void testReadJsonString() {
        String filename = "d:/invoice.json";
        String json = JSONUtil.readJsonString(filename);
        System.out.println(json);
    }
}
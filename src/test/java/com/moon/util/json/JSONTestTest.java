package com.moon.util.json;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class JSONTestTest {

    @Test
    void testParse() {
        String filename = "d:/invoice.json";
        JSON json = JSON.parse(new File(filename));
        System.out.println();
    }
}
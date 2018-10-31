package com.moon.util;

import com.moon.enums.PredicateEnum;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.moon.util.assertions.Assertions.of;

/**
 * @author benshaoye
 */
class PropertiesUtilTestTest {
    static final Assertions assertions = of();
    static String[] paths = {
        "/test1.properties",
        "/test2.properties",
        "/test3.properties",
    };
    String path = FilterUtil.requireFirst(paths, PredicateEnum.NON_NULL);


    static final Assertions ASSERTIONS = of();

    @Test
    void testRefreshAll() {

    }

    @Test
    void testLoad() {
    }

    @Test
    void testGet() {
        Map map = PropertiesUtil.get(path);
        ASSERTIONS.assertNotNull(map);
        ASSERTIONS.assertNotEmpty(map);
    }

    @Test
    void testGetString() {
//        final Assertions assertions = Assertions.ofPrintln();
        String email = PropertiesUtil.getString(path, "email.host");
        String email1 = PropertiesUtil.getString(path, "email.host1");

        assertions.assertNotNull(email);
        assertions.assertNull(email1);
        assertions.assertNull(email1);
    }

    @Test
    void testGetIntValue() {
    }

    @Test
    void testGetLongValue() {
    }

    @Test
    void testGetDoubleValue() {
    }

    @Test
    void testGetBooleanValue() {
    }

    @Test
    void testGetOrDefault() {
    }
}
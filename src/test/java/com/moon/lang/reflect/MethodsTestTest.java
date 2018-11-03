package com.moon.lang.reflect;

import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author benshaoye
 */
class MethodsTestTest {

    List<Method> methods;
    Method m;
    static final Assertions assertions = Assertions.of();

    public static class Util {
        public void parse(String s, String s1) {

        }

        public void parse(CharSequence s, String s1) {

        }
    }

    @Test
    void testGetPublicMethods() {
    }
}
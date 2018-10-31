package com.moon.util;

import com.moon.lang.reflect.MethodUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author benshaoye
 */
public class OSUtilTest {
    @Test
    void testIsLinux() {
        try {
            List<Method> methodList = MethodUtil.getPublicStaticMethods(OSUtil.class);
            String[] tests = new String[methodList.size()];
            IteratorUtil.forEach(methodList, (method, index) ->
                tests[index] = MethodUtil.invokeStatic(method) + "\t" + method.getName());
            Arrays.sort(tests);
            IteratorUtil.forEach(tests, System.out::println);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

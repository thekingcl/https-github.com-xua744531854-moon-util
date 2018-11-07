package com.moon.script;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class ScriptUtilTestTest {
    static final Assertions assertions = Assertions.of();
    Object res;

    @Test
    void testRunJSCode() {
        res = ScriptUtil.runJSCode("1+1");
        assertions.assertEquals(res, 2);
    }
}
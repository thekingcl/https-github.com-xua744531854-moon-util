package com.moon.util;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class TypeUtilTestTest {

    static final Assertions assertions = Assertions.of();

    @Test
    void testCast() {
        assertions.assertEquals(TypeUtil.cast().toBooleanValue(0), false);
    }
}
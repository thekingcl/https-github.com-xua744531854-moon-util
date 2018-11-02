package com.moon.lang.ref;

import com.moon.lang.reflect.ConstructorUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.WeakHashMap;

/**
 * @author benshaoye
 */
class WeakMapManagerTestTest {
    static final Assertions assertions = Assertions.of();

    @Test
    void testManage() {
        assertions.assertThrows(() -> ConstructorUtil.newInstance(WeakMapManager.class, true));
        assertions.assertThrows(() -> WeakMapManager.manage(new WeakHashMap<>()));
    }
}
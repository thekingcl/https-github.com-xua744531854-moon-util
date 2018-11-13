package com.moon.util.compute;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
class RunnerDataMapTestTest {
    static final Assertions assertions = Assertions.of();

    Map dataMap = new RunnerDataMap();
    Map hashMap = new HashMap();

    @Test
    void testGet() {
        dataMap.put("name", 1);
        hashMap.put("name", 1);
        assertions.assertEquals(dataMap.get("name"), hashMap.get("name"));
    }

    @Test
    void testKeySet() {
        Object[] data = {
            new HashMap() {{
                put("name", 1);
                put("age", 3);
                put("sex", 4);
            }},
            new String[]{"A", "B"},
        };
        dataMap = new RunnerDataMap(data);
        assertions.assertEq(dataMap.size(), 5);

        assertions.assertEquals(dataMap.get(0), "A");
        assertions.assertEquals(dataMap.get(1), "B");
        assertions.assertEquals(dataMap.get("age"), 3);
    }
}
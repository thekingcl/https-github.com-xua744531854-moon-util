package com.moon.lang;

import com.moon.lang.ref.IntAccessor;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
public class SupportUtilTest {
    @Test
    void testParseStr() {
        char[] chars = "111\\'222 '".toCharArray();
        IntAccessor indexer = IntAccessor.of();
        String str = SupportUtil.parseStr(chars, indexer, '\'');
        System.out.println(str);
    }
}

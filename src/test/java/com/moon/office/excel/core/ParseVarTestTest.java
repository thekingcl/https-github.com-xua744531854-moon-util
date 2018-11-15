package com.moon.office.excel.core;

import com.moon.enums.ArraysEnum;
import com.moon.lang.ref.IntAccessor;
import com.moon.office.excel.core.ParseVar;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class ParseVarTestTest {

    String[] parseKeys(String var) {
        char[] chars = var.trim().toCharArray();
        return chars.length > 0 ? ParseVar.parseKeys(chars, IntAccessor.of(), chars.length) : ArraysEnum.STRINGS.empty();
    }

    static final Assertions assertions = Assertions.of();

    String var;
    String[] keys;

    @Test
    void testParseKeys() {
        var = " sdfuhaskdjf   ";
        keys = parseKeys(var);
        assertions.assertEq(keys.length, 1);
        assertions.assertEquals(keys[0], "sdfuhaskdjf");

        var = " (sdfuhaskdjf)   ";
        keys = parseKeys(var);
        assertions.assertEq(keys.length, 1);
        assertions.assertEquals(keys[0], "sdfuhaskdjf");

        var = " (sdfuhaskdjf,name)   ";
        keys = parseKeys(var);

        assertions.assertEq(keys.length, 2);
        assertions.assertEquals(keys[0], "sdfuhaskdjf");
        assertions.assertEquals(keys[1], "name");

        assertions.assertThrows(() -> parseKeys("1 "));
        assertions.assertThrows(() -> parseKeys(" (name,sex,age "));

        keys = parseKeys("");
    }
}
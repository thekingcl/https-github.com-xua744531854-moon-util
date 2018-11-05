package com.moon.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author benshaoye
 */
class StringJoinerTestTest {

    @Test
    void testOf() {
        assertDoesNotThrow(() -> {
            StringJoiner.of("");
        });
    }

    @Test
    void testAdd() {
        Object obj = null;
        StringJoiner joiner = JoinerUtil.of(",", "(", ")").add("aaa").add(obj).add("bbb").add(null);
        assertEquals(joiner.toString(), "(aaa,null,bbb,null)");
    }

    @Test
    void testJoin() {
        Object obj = null;
        List<String> list = new ArrayList() {{
            add("aaaa");
            add("bbbb");
            add(null);
            add("cccc");
            add("dddd");
            add("eeee");
        }};
        StringJoiner joiner = JoinerUtil.of(",", "( ", " )")
            .useForNull("本少爷")
            .add("aaa").add(obj).add("bbb").add(null)
            .addDelimiter().setDelimiter("|")
            .useForNull("moonsky").join(list);
        System.out.println(joiner);
        joiner = JoinerUtil.of("[]").skipNulls()
            .useForNull("本少爷")
            .add("aaa").add(obj).add("bbb").add(null)
            .addDelimiter().setDelimiter("|")
            .useForNull("moonsky").join(list);
        System.out.println(joiner);

        joiner = JoinerUtil.of(null)
            .add("SELECT * FROM table WHERE name IN (")
            .addDelimiter().setDelimiter(",").join(list).setDelimiter(null).add(")");
        System.out.println(joiner);
    }

    @Test
    void testMerge() {
        Object obj = null;
        List<String> list = new ArrayList() {{
            add("aaaa");
            add("bbbb");
            add(null);
            add("cccc");
            add("dddd");
            add("eeee");
        }};
        JoinerUtil.of(";").join(list);
        assertEquals("aaaa;bbbb;null;cccc;dddd;eeee", JoinerUtil.of(";").join(list).toString());

        assertEquals("aaaa;bbbb;cccc;dddd;eeee", JoinerUtil.of(";").skipNulls().join(list).toString());
    }

    @Test
    void testSetRequiredNonNullItem() {
    }

    @Test
    void testUseForNull() {
    }

    @Test
    void testSetDelimiter() {
    }

    @Test
    void testToString() {
    }


}
package com.moon.util.compute.core;

import com.moon.util.MapUtil;
import com.moon.util.assertions.Assertions;
import com.moon.util.compute.RunnerUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author benshaoye
 */
class ParseDelimitersTestTest {

    static final Assertions assertions = Assertions.of();

    static AsRunner running(String expression) {
        return running(expression, RunnerUtil.DELIMITERS);
    }

    static AsRunner running(String expression, String[] delimiters) {
        return ParseDelimiters.parse(expression, delimiters);
    }

    Object data;
    String str, str1;
    String[] strs;
    AsRunner handler, handler1;

    @Test
    void testParse() {
        str = "本草纲目";
        handler = running(str);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), str);

        str = "本草纲目{{'好的'}}";
        handler = running(str);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的");

        str = "本草纲目{{'好的'}}  {{123}}";
        handler = running(str);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123");

        str = "本草纲目{{'好的'}}  {{123}}  ";
        handler = running(str);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123  ");

        str = "本草纲目{{'好的'}}  {{123}}  电脑 {{1+2+3+5+6}}";
        handler = running(str);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123  电脑 17");
    }

    @Test
    void testParseGetter() {
        data = new HashMap() {{
            put("name", 456);
        }};
        str = "本草纲目{{'好的'}}  {{123}}  电脑 {{1+2+3+5+6}} {{name}}";
        str1 = "本草纲目{{'好的'}}  {{123}}{{['true']}}  电脑 {{1+2+3+5+6}} {{name}}";
        handler = running(str);
        handler1 = running(str1);
        assertions.assertInstanceOf(handler, AsGetter.class);
        assertions.assertEquals(handler.run(data), "本草纲目好的  123  电脑 17 456");

        MapUtil.putToObject(data, "name", "就问你嗨不嗨");
        MapUtil.putToObject(data, "true", "你是不是傻");

        assertions.assertEquals(handler.run(data), "本草纲目好的  123  电脑 17 就问你嗨不嗨");
        assertions.assertEquals(handler1.run(data), "本草纲目好的  123你是不是傻  电脑 17 就问你嗨不嗨");
    }

    @Test
    void testParse1() {
        strs = new String[]{"${", "}}"};
        str = "本草纲目";
        handler = running(str, strs);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), str);

        str = "本草纲目${'好的'}}";
        handler = running(str, strs);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的");

        str = "本草纲目${'好的'}}  ${123}}";
        handler = running(str, strs);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123");

        str = "本草纲目${'好的'}}  ${123}}  ";
        handler = running(str, strs);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123  ");

        str = "本草纲目${'好的'}}  ${123}}  电脑 ${1+2+3+5+6}}";
        handler = running(str, strs);
        assertions.assertInstanceOf(handler, DataConstString.class);
        assertions.assertEquals(handler.run(), "本草纲目好的  123  电脑 17");
    }

    @Test
    void testParseGetter1() {
        strs = new String[]{"${", "}}"};
        data = new HashMap() {{
            put("name", 456);
        }};
        str = "本草纲目${'好的'}}  ${123}}  电脑 ${1+2+3+5+6}} ${name}}";
        str1 = "本草纲目${'好的'}}  ${123}}${['true']}}  电脑 ${1+2+3+5+6}} ${name}}";
        handler = running(str, strs);
        handler1 = running(str1, strs);
        assertions.assertInstanceOf(handler, AsGetter.class);
        assertions.assertEquals(handler.run(data), "本草纲目好的  123  电脑 17 456");

        MapUtil.putToObject(data, "name", "就问你嗨不嗨");
        MapUtil.putToObject(data, "true", "你是不是傻");
        MapUtil.putToObject(data, true, "回长沙");

        assertions.assertEquals(handler.run(data), "本草纲目好的  123  电脑 17 就问你嗨不嗨");
        assertions.assertEquals(handler1.run(data), "本草纲目好的  123你是不是傻  电脑 17 就问你嗨不嗨");

        str1 = "本草纲目${'好的'}}  ${123}}${['true']}} ${[true]+true}}  电脑 ${1+2+3+5+6}} ${name}}";
        handler1 = running(str1, strs);
        assertions.assertEquals(handler1.run(data), "本草纲目好的  123你是不是傻 回长沙true  电脑 17 就问你嗨不嗨");

        str1 = "本草纲目${'好的'}}  ${123}}${['true']}} ${[true]+true+name}}  电脑 ${1+2+3+5+6}} ${name}}";
        handler1 = running(str1, strs);
        str = "本草纲目好的  123你是不是傻 回长沙true就问你嗨不嗨  电脑 17 就问你嗨不嗨";
        assertions.assertEquals(handler1.run(data), str);
    }
}
package com.moon.util.compute;

import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author benshaoye
 */
class RunnerUtilTestTest {
    static final Assertions assertions = Assertions.of();
    Object data, res;

    @Test
    void testMapAndList() {
        res = RunnerUtil.run("{:}.get(true)");
        assertions.assertEquals(res, null);
        res = RunnerUtil.run("{}.isEmpty()");
        assertions.assertEquals(res, true);
        res = RunnerUtil.run("{:}.isEmpty()");
        assertions.assertEquals(res, true);

        res = RunnerUtil.run("{a:10}.isEmpty()");
        assertions.assertEquals(res, false);

        assertions.assertThrows(()-> RunnerUtil.run("{a:}.isEmpty()"));
        assertions.assertThrows(()-> RunnerUtil.run("{a}.isEmpty()"));

        res = RunnerUtil.run("{a:10}.isEmpty() + false");
        assertions.assertEquals(res, "falsefalse");
    }

    @Test
    void testRun() {
        res = RunnerUtil.run("1+1.doubleValue()");
        assertions.assertEquals(res, 2D);
        res = RunnerUtil.run("''.length()");
        assertions.assertEquals(res, 0);
        res = RunnerUtil.run("1+1.doubleValue()");
        assertions.assertEquals(res, 2D);
        res = RunnerUtil.run("'a'.length()");
        assertions.assertEquals(res, 1);
        res = RunnerUtil.run("@DateUtil.yyyy_MM+20");
        assertions.assertEquals(res, "yyyy-MM20");
        res = RunnerUtil.run("@   DateUtil.yyyy_MM+20");
        assertions.assertEquals(res, "yyyy-MM20");
    }


    @Test
    void testCalc() {
        assertions.assertEquals(RunnerUtil.run("1^1"), 1 ^ 1);
        assertions.assertEquals(RunnerUtil.run("2^1+5"), 2 ^ 1 + 5);
    }

    @Test
    void testParseRun() {
        assertions.assertEquals(RunnerUtil.parseRun("{{1+2}}"), 3);
        assertions.assertEquals(RunnerUtil.parseRun("{{'中华人民共和国'}}"), "中华人民共和国");

        data = new HashMap() {{
            put("name", 456);
        }};

        str = "本草纲目{{'好的'}}  {{   123   }}  电脑 {{1+2+3+5+6}} {{name}}";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, data),
            "本草纲目好的  123  电脑 17 456"
        );

        str = "本草纲目{{'好的'}}  {{123}}  ";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, data),
            "本草纲目好的  123  "
        );
    }

    @Test
    void testParseRunPerformance() {
        int count = 100000;
        long begin = DateUtil.now();
        for (int i = 0; i < count; i++) {
            RunnerUtil.parseRun("{{1+2}}");
        }
        long end = DateUtil.now();
        Console.out.println(end - begin);
    }

    String str, result;
    String[] delimiters = new String[]{"${", "}}"};

    @Test
    void testParseRun1() {
        data = new HashMap() {{
            put("name", 456);
        }};

        str = "本草纲目${'好的'}}  ${123}}  电脑 ${1+2+3+5+6}} ${name}}";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, delimiters, data),
            "本草纲目好的  123  电脑 17 456"
        );

        str = "本草纲目${'好的'}}  ${123}}  ";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, delimiters, data),
            "本草纲目好的  123  "
        );
    }

    @Test
    void testParseRun2() {
    }
}
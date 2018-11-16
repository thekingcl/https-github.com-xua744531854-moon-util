package com.moon.util.compute;

import com.moon.lang.ClassUtil;
import com.moon.lang.DoubleUtil;
import com.moon.lang.reflect.MethodUtil;
import com.moon.script.ScriptUtil;
import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Objects;

import static com.moon.util.assertions.Assertions.of;

/**
 * @author benshaoye
 */
class RunnerUtilTestTest {
    static final Assertions assertions = of();
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

        assertions.assertThrows(() -> RunnerUtil.run("{a:}.isEmpty()"));
        assertions.assertThrows(() -> RunnerUtil.run("{a:,}.isEmpty()"));
        assertions.assertThrows(() -> RunnerUtil.run("{a}.isEmpty()"));

        res = RunnerUtil.run("{a:10}.isEmpty() + false");
        assertions.assertEquals(res, "falsefalse");

        res = RunnerUtil.run("!{:}.isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!{}.isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({:}.isEmpty())");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({}.isEmpty())");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({:}).isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({}).isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("-20.doubleValue()");
        assertions.assertEquals(res, -20D);

        res = RunnerUtil.run("-20.doubleValue().intValue()");
        assertions.assertEquals(res, -20);

        res = RunnerUtil.run("-@DateUtil.now().intValue()");
        assertions.assertInstanceOf(res, Integer.class);
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
        res = RunnerUtil.run("@Long.  parseLong(   @Objects.toString(   @DateUtil.now(   )   )  )");
        res = RunnerUtil.run("-(-@Long.parseLong(   @   Objects  . toString( @    DateUtil .  now(   ))  )   ).longValue()");
        assertions.assertInstanceOf(res, Long.class);
        res = RunnerUtil.run("@MapUtil.sizeByObject({key: null, null: true})");
        assertions.assertInstanceOf(res, Integer.class);
        assertions.assertEquals(res, 2);
        res = RunnerUtil.run("{key: null, null: true, 25.3: 25}");
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.sizeByObject(res), 3);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), null);
        assertions.assertEquals(MapUtil.getByObject(res, null), true);
        assertions.assertEquals(MapUtil.getByObject(res, null), true);
        assertions.assertEquals(MapUtil.getByObject(res, "null"), null);
        assertions.assertEquals(MapUtil.getByObject(res, 25.3), 25);
    }

    @Test
    void testGetMethod() {
        res = MethodUtil.getPublicStaticMethods(Objects.class, "hash");
        Console.out.println((Object) ListUtil.getByObject(res, 0));
    }

    @Test
    void testParseMultipleParamMethod() {
        res = ClassUtil.getClasses(1, 2.0);
        res = MethodUtil.getPublicStaticMethods(DoubleUtil.class, "requireGt", (Class[]) res);
        //res = RunnerUtil.run("@DateUtil.parse('2018-05-09 12:35:26', 'yyyy-MM-dd HH:mm:ss')");

        assertions.assertSame(String.class.getPackage(), Class.class.getPackage());
        data = new HashMap() {{
            put("arr", new Object[]{1, 2, 3});
        }};
        res = RunnerUtil.run("arr.length", data);
        System.out.println(res);
    }

    @Test
    void testRunningTimes() throws ScriptException {
        ScriptEngine engine = ScriptUtil.newJSEngine();
        System.out.println(engine.eval("1+1"));
        System.out.println(RunnerUtil.run("1+1"));
        Runner runner = RunnerUtil.parse("1+1");

        int count = 1000000;
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = engine.eval("1+1");
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = RunnerUtil.run("1+1");
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = runner.run();
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = 1 + 1;
        }
        Console.out.timeEnd();
    }

    @Test
    void testParseToRunner() {
        Runner runner = RunnerUtil.parse("{'已有人数','需要人数','已提交','已付款','已完成'}");
        res = runner.run();
        System.out.println(res);
    }
}
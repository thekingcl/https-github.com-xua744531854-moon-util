package com.moon.util.compute.core;

import com.moon.io.FileUtil;
import com.moon.lang.StringUtil;
import com.moon.util.Console;
import com.moon.util.MapUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author benshaoye
 */
class ParseCoreTestTest {

    static final Assertions assertions = Assertions.of();

    int num;
    String str;
    Object data;
    Object res;
    AsRunner handler, handler1;

    static AsRunner running(String str) {
        return ParseCore.parse(str);
    }

    @Test
    void testCompare() {
        handler = running("1>2");
        assertions.assertEquals(handler.run(), false);
        handler = running("1+2>2");
        assertions.assertEquals(handler.run(), true);
        handler = running("1>=2");
        assertions.assertEquals(handler.run(), false);
        handler = running("1+2>=2");
        assertions.assertEquals(handler.run(), true);
        handler = running("2>=2");
        assertions.assertEquals(handler.run(), true);
        handler = running("1+2>=3");
        assertions.assertEquals(handler.run(), true);
    }

    @Test
    void testRunningCaller() {
        handler = running("@Objects.hash('123')");
        assertions.assertEquals(Objects.hash("123"), handler.run());
        handler = running("@StringUtil.concat(data)");
        data = new HashMap() {{
            put("data", new CharSequence[]{"123", "456"});
        }};
        assertions.assertEquals(StringUtil.concat("123", "456"), handler.run(data));
    }

    @Test
    void testGetTotalFiles() {
        String path = "D:\\WorkSpaces\\IDEA\\moonsky\\src\\main\\java\\com\\moon";
        List<File> all = FileUtil.traverseDirectory(path);
        Console.out.println("==============================================");
        Console.out.println(all.size());
    }

    @Test
    void testName() {
        handler = running("1 + 2 + @Objects.toString('10')");
        assertions.assertEquals(handler.run(), "310");
        handler = running("@Console.out.getLowestLevel().name()");
        assertions.assertEquals(handler.run(), "PRINT");
        handler = running("@Console$Level.ASSERT.name()");
        assertions.assertEquals(handler.run(), "ASSERT");
    }

    @Test
    void testGetOpposite() {
        handler = ParseCore.parse("1+-1*5");
        assertions.assertEq((Integer) handler.run(), 1 + -1 * 5);
        handler = ParseCore.parse("(1+-1)*5");
        assertions.assertEq((Integer) handler.run(), (1 + -1) * 5);
        handler = ParseCore.parse("-1*5");
        assertions.assertEq((Integer) handler.run(), -1 * 5);

        data = new HashMap() {{
            put(true, 20);
        }};
        handler = ParseCore.parse("-[true]*5");
        assertions.assertEq((Integer) handler.run(data), -100);
    }

    @Test
    void testParse() {
        handler = ParseCore.parse("1+1");
        assertions.assertEq((Integer) handler.run(), 2);
        handler = ParseCore.parse("(1+1+205)");
        assertions.assertEq((Integer) handler.run(), 207);
        handler = ParseCore.parse("2100-21*53+2255");
        num = 2100 - 21 * 53 + 2255;
        assertions.assertEq((Integer) handler.run(), num);
        handler = ParseCore.parse("40 * 48 - (1472 + 328) / 5");
        num = 40 * 48 - (1472 + 328) / 5;
        assertions.assertEq((Integer) handler.run(), num);

        str = "aaaaaaaaaaa";
        data = new HashMap() {{
            put(true, str);
            put(false, false);
            put(str, false);
            put(20, new HashMap() {{
                put("name", 53);
            }});
        }};

        handler = ParseCore.parse("[true]");
        assertions.assertEquals(handler.run(data), str);
    }

    @Test
    void testInvoker() {
        data = new HashMap() {{
            put(20, new HashMap() {{
                put("name", new ArrayList() {{
                    add(new Employee());
                    add(new Employee());
                }});
            }});
            put(true, 16);
            put("true", 20);
        }};

        handler = ParseCore.parse("[20].get('name').get(0).age.toString().length()");
        assertions.assertEquals(handler.run(data), 2);

        handler = ParseCore.parse(
            "[  20   ]  .name   .  get(0)  .  age  .  doubleValue(). toString(   ) . length   (  ) + [true]");
        assertions.assertEquals(handler.run(data), 20);

        handler = ParseCore.parse(
            "[  20   ]  .name   .  get(0)  .  age  .  doubleValue(). toString(   ) . length   (  ) + ['true']");
        assertions.assertEquals(handler.run(data), 24);

        handler = ParseCore.parse("([20].name.get(0).age.doubleValue().toString().length()+[true]).longValue()");
        assertions.assertEquals(handler.run(data), 20L);

        handler = ParseCore.parse("([20].name.get(0).age.doubleValue().toString().length()+['true'])");
        assertions.assertEquals(handler.run(data), 24);

        handler = ParseCore.parse("([20].name.get(0).age.doubleValue().toString().length()+true).toString().length()");
        assertions.assertEquals(handler.run(data), 5);
    }

    @Test
    void testCaller() {
        data = new HashMap() {{
            put("array", new Object[]{1, 2, 3, "asdfghj"});
        }};
        handler = ParseCore.parse("@System.currentTimeMillis()");
        res = handler.run();
        Console.out.println(res);
        handler1 = ParseCore.parse("@DateUtil.now()");
        res = handler.run();
        Console.out.println(res);
        Console.out.println("===============================");
        res = handler.run();
        Console.out.println(res);
        res = handler1.run();
        Console.out.println(res);
        Console.out.println("===============================");
    }

    @Test
    void testArrayStringify() {
        data = new HashMap() {{
            put("array", new Object[]{1, 2, 3, "asdfghj"});
        }};
        handler1 = ParseCore.parse("@ ArraysEnum . OBJECTS . stringify(['array']).toString()");
        res = handler1.run(data);

    }

    @Test
    void testCalc() {
        res = ParseCore.parse("15+5/3").run();
        Console.out.println(res);
    }

    @Test
    void testLinker() {
        str = "1111111111111";
        String str1 = "aaaaaa";
        data = new HashMap() {{
            put(20, new HashMap() {{
                put("name", new ArrayList() {{
                    add(new Employee());
                    add(new Employee());
                }});
            }});
            put("fieldName", "age");
            put(str1, str1);
            put(true, str);
            put(false, false);
            put(str, false);
        }};

        handler = ParseCore.parse("[true]");
        assertions.assertEquals(handler.run(data), str);
        handler = ParseCore.parse("[aaaaaa]");
        assertions.assertEquals(handler.run(data), str1);
        handler = ParseCore.parse("!['1111111111111']");
        assertions.assertTrue((Boolean) handler.run(data));

        MapUtil.putToObject(data, true, "age");

        handler = ParseCore.parse("(!['1111111111111']+([20].name[1][fieldName].doubleValue() + '123')).length()");
        assertions.assertEquals(handler.run(data), 11);
        handler = ParseCore.parse("(!['1111111111111']+([20].name[1][fieldName].doubleValue() + '123'))");
        assertions.assertEquals(handler.run(data), "true20.0123");
        handler = ParseCore.parse("([20].name).isEmpty()");
        assertions.assertFalse((Boolean) handler.run(data));
        handler = ParseCore.parse("({}).isEmpty()");
        assertions.assertTrue((Boolean) handler.run(data));
        handler = ParseCore.parse("({:}).isEmpty()");
        assertions.assertTrue((Boolean) handler.run(data));
        handler = ParseCore.parse("({}).size()");
        assertions.assertEquals(handler.run(data), 0);
        handler = ParseCore.parse("({:}).size()");
        assertions.assertEquals(handler.run(data), 0);
    }

    public static class Employee {
        int age = 20;
    }

    @Test
    void testInstance() {
        Object o = null;
        Console.out.println(Object.class.isInstance(o));
    }

    @Test
    void testCalculator() {
        assertions.assertEq(((Number) ParseCore.parse("349+644+72").run()).intValue(), 349 + 644 + 72);
    }
}
package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;
import com.moon.util.Console;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author benshaoye
 */
class ParseCurlyTestTest {

    static final Assertions assertions = Assertions.of();

    static AsRunner running0(String expression) {
        char[] chars = expression.toCharArray();
        IntAccessor indexer = IntAccessor.of();
        int length = chars.length;
        ParseUtil.skipWhitespaces(chars, indexer, length);
        return ParseCurly.parse(chars, indexer, length);
    }

    String str;
    AsRunner handler;
    Object res, data;

    @Test
    void testParse0() {
        doEmpty();
        doList();
        doMap();
        doVarMap();
        doVarList();

        assertions.assertThrows(() -> running0("{key: 'value', : (50 + 60   ), true: @DateUtil.now()+0}"));
    }

    void doVarList() {
        data = running0("{key: 'value', null: (50 + 60   ), true: @DateUtil.now()+0}").run();

        str = "{key, key,true,[true],[null]}";
        handler = running0(str);
        assertions.assertThrows(() -> handler.run());
        res = handler.run(data);
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEquals(ListUtil.getByObject(res, 0), "value");
        assertions.assertEquals(ListUtil.getByObject(res, 1), "value");
        assertions.assertEquals(ListUtil.getByObject(res, 2), true);
        assertions.assertInstanceOf(ListUtil.getByObject(res, 3), Double.class);
        assertions.assertEquals(ListUtil.getByObject(res, 4), 110);
    }

    void doVarMap() {
        data = running0("{key: 'value', null: (50 + 60   ), true: @DateUtil.now()+0}").run();

        str = "{key: key}";
        handler = running0(str);
        assertions.assertThrows(() -> handler.run());
        res = handler.run(data);
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");

        str = "{key: 'value', null: [null]}";
        handler = running0(str);
        assertions.assertThrows(() -> handler.run());
        res = handler.run(data);
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");
        assertions.assertEquals(MapUtil.getByObject(res, null), 110);
        assertions.assertInstanceOf(MapUtil.getByObject(data, true), Double.class);
        Console.out.println(((Number) MapUtil.getByObject(data, true)).longValue());
    }

    void doMap() {
        str = "{key: 'value'}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEq(MapUtil.sizeByObject(res), 1);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");

        str = "{key: 'value',}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEq(MapUtil.sizeByObject(res), 1);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");

        str = "{key: 'value', null: 20}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEq(MapUtil.sizeByObject(res), 2);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");
        assertions.assertEquals(MapUtil.getByObject(res, null), 20);

        str = "{key: 'value', null: 20, true: '100asdfasdf'}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEq(MapUtil.sizeByObject(res), 3);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");
        assertions.assertEquals(MapUtil.getByObject(res, null), 20);
        assertions.assertEquals(MapUtil.getByObject(res, true), "100asdfasdf");

        str = "{key: 'value', null: 20, true: '100asdfasdf'  ,   false  : \"sdfasdfgawfasdfasdf\"}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEq(MapUtil.sizeByObject(res), 4);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), "value");
        assertions.assertEquals(MapUtil.getByObject(res, null), 20);
        assertions.assertEquals(MapUtil.getByObject(res, true), "100asdfasdf");
        assertions.assertEquals(MapUtil.getByObject(res, false), "sdfasdfgawfasdfasdf");

        str = "{key: 'value', null: 20, true: '100asdfasdf'  ,  false  : \"sdfasdfgawfasdfasdf\", 20: 150}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, 20), 150);

        str = "{key: 'value', null: 20, true: '100asdfasdf',  1503.2: 110}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, 1503.2), 110);

        str = "{key: 'value', null: 20, true: '100asdfasdf',,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null:: 20, true: '100asdfasdf',,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null: 20, true: '100asdfasdf'   ,    ,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null    :    : 20, true: '100asdfasdf',,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null , null   :    : 20, true: '100asdfasdf',,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null   : null,    : 20, true: '100asdfasdf',,  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));

        str = "{key: 'value', null: 20, true: '100asdfasdf',  1503.2: (110)}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, 1503.2), 110);

        str = "{key: 'value', null: 20+@DateUtil.now(), true: '100asdfasdf'+@DateUtil.now(),  1503.2: (110)}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.getByObject(res, 1503.2), 110);
        assertions.assertInstanceOf(MapUtil.getByObject(res, true), String.class);
        assertions.assertInstanceOf(MapUtil.getByObject(res, null), Double.class);

        str = "{key: 'value', null   : null,  '  : 20, true: '100asdfasdf',  1503.2: 110}";
        assertions.assertThrows(() -> handler = running0(str));
    }

    void doList() {

        str = "{,}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 1);

        str = "  {  ,  }  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 1);

        str = "  {  ,  ,}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 2);

        str = "  {  ,  ,,,,}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 5);

        str = "  {  , null , true, false,20,30,'50'}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 7);

        int index = 0;
        assertions.assertEquals(ListUtil.getByObject(res, index++), null);
        assertions.assertEquals(ListUtil.getByObject(res, index++), null);
        assertions.assertEquals(ListUtil.getByObject(res, index++), true);
        assertions.assertEquals(ListUtil.getByObject(res, index++), false);
        assertions.assertEquals(ListUtil.getByObject(res, index++), 20);
        assertions.assertEquals(ListUtil.getByObject(res, index++), 30);
        assertions.assertEquals(ListUtil.getByObject(res, index++), "50");


        str = "  {  ,  (),,(null),,}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 5);
        assertions.assertEquals(ListUtil.getByObject(res, 3), null);

        str = "  { 30+60+110 , null , true, false,20,30,'50'}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 7);
        assertions.assertEquals(ListUtil.getByObject(res, 0), 200);

        str = "  { 30+60+110 ,(30+60+110 + 2 * 20), null , true, false,20,30,'50'}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 8);
        assertions.assertEquals(ListUtil.getByObject(res, 1), 240);

        str = "  { 30+60+110 ,(30+60+110 + 2 * 20 + @DateUtil.now()), null , true, false,20,30,'50'}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 8);
        assertions.assertInstanceOf(ListUtil.getByObject(res, 1), Double.class);

        str = "  { 30+60+110,@Objects.toString(20),(30+60+110 + 2 * 20 + @DateUtil.now()), null , true, false,20,30,'50'}  ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEq(ListUtil.sizeByObject(res), 9);
        assertions.assertInstanceOf(ListUtil.getByObject(res, 2), Double.class);

        Object o = ListUtil.getByObject(res, 1);
        assertions.assertInstanceOf(o, String.class);
        assertions.assertEquals(o, "20");
    }

    void doEmpty() {
        doEmpty0();
        doEmpty1();
        doEmpty2();
        doEmpty3();
    }

    void doEmpty0() {
        str = "   {   }    ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEmpty((List) res);

        str = "    {  :    } ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEmpty((Map) res);
    }

    void doEmpty1() {
        str = "   {}   ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEmpty((List) res);

        str = "    {:}              ";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEmpty((Map) res);
    }

    void doEmpty2() {
        str = "{         }";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEmpty((List) res);

        str = "{       :       }";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEmpty((Map) res);
    }

    void doEmpty3() {
        str = "{}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, ArrayList.class);
        assertions.assertEmpty((List) res);
        str = "{:}";
        handler = running0(str);
        res = handler.run();
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEmpty((Map) res);
    }
}
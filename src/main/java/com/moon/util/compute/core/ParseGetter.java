package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import java.util.Objects;

import static com.moon.lang.SupportUtil.setChar;
import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
final class ParseGetter {
    private ParseGetter() {
        noInstanceError();
    }

    /**
     * 返回一个 valuer
     *
     * @param chars
     * @param indexer
     * @param len
     * @param current
     * @return
     */
    final static AsValuer parseVar(char[] chars, IntAccessor indexer, int len, int current) {
        char curr = (char) current;
        char[] value = {curr};
        int index = value.length, i = indexer.get();
        for (; i < len && ParseUtil.isVar(curr = chars[i]) || ParseUtil.isNum(curr); i++) {
            value = setChar(value, index++, curr);
        }
        ParseUtil.setIndexer(indexer, i, len);
        String str = ParseUtil.toStr(value, index);
        switch (str) {
            case "null":
                return DataConstNull.NULL;
            case "true":
                return DataConstBoolean.TRUE;
            case "false":
                return DataConstBoolean.FALSE;
            default:
                return new DataGetterOrdinary(str);
        }
    }

    /**
     * 返回一个 getter
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    final static AsValuer parseDot(char[] chars, IntAccessor indexer, int len) {
        int curr = ParseUtil.skipWhitespace(chars, indexer, len);
        ParseUtil.assertTrue(ParseUtil.isVar(curr), chars, indexer);
        return parseVar(chars, indexer, len, curr);
    }

    final static AsHandler parseNot(char[] chars, IntAccessor indexer, int len) {
        AsHandler valuer;
        int curr = ParseUtil.skipWhitespace(chars, indexer, len);
        if (ParseUtil.isVar(curr)) {
            valuer = parseVar(chars, indexer, len, curr);
            ParseUtil.assertFalse(valuer == DataConstNull.NULL, chars, indexer);
            if (valuer == DataConstBoolean.TRUE) {
                valuer = DataConstBoolean.FALSE;
            } else if (valuer == DataConstBoolean.FALSE) {
                valuer = DataConstBoolean.TRUE;
            } else {
                valuer = new DataGetterNot(valuer);
            }
        } else if (curr == Constants.FANG_LEFT) {
            valuer = new DataGetterNot(parseFang(chars, indexer, len));
        } else {
            valuer = ParseUtil.throwErr(chars, indexer);
        }
        return valuer;
    }

    /**
     * 返回一个getter
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    final static DataGetterFang parseFang(char[] chars, IntAccessor indexer, int len) {
        AsHandler handler = ParseCore.parse(chars, indexer, len, Constants.FANG_RIGHT);
        ParseUtil.assertTrue(handler.isValuer(), chars, indexer);
        return new DataGetterFang((AsValuer) handler);
    }

    final static AsHandler parseYuan(char[] chars, IntAccessor indexer, int len) {
        return ParseCore.parse(chars, indexer, len, Constants.YUAN_RIGHT);
    }

    final static AsHandler parseCaller(char[] chars, IntAccessor indexer, int len) {
        int curr = chars[indexer.get()];
        AsHandler handler;
        if (curr == Constants.CALLER) {
            // 为自定义静态类留位置，调用方式是两个‘@’：@@CustomType.method()
            throw new UnsupportedOperationException("未来将支持 ‘@@’ 符号，供自定义静态方法字段调用");
        } else {
            curr = ParseUtil.skipWhitespace(chars, indexer, len);
            if (ParseUtil.isVar(curr)) {
                handler = parseVar(chars, indexer, len, curr);
                handler = new DataConstLoader(ILoader.of(handler.toString()));
            } else {
                // 为更多符号留位置，比如动态变化的类，等
                throw new UnsupportedOperationException("未来将支持动态类的构造");
            }
        }
        return Objects.requireNonNull(handler);
    }
}

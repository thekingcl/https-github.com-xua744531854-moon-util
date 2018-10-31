package com.moon.util.compute.core;

import com.moon.lang.CharUtil;
import com.moon.lang.ref.IntAccessor;

import java.util.HashMap;
import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static java.lang.Character.isWhitespace;

/**
 * @author benshaoye
 */
public class ParseUtil {
    protected ParseUtil() {
        noInstanceError();
    }

    /*
     * -----------------------------------------------
     * assertions
     * -----------------------------------------------
     */

    final static boolean isNum(int value) {
        return value > 47 && value < 58;
    }

    final static boolean isVar(int value) {
        return CharUtil.isLetter(value)
            || value == '$' || value == '_'
            || CharUtil.isChinese(value);
    }

    final static boolean isStr(int value) {
        return value == Constants.SINGLE_QUOTE || value == Constants.DOUBLE_QUOTE;
    }

    final static void setIndexer(IntAccessor indexer, int index, int len) {
        indexer.set(index < len ? index : len);
    }

    /*
     * -----------------------------------------------
     * tools
     * -----------------------------------------------
     */

    final static String toStr(char[] chars, int len) {
        return new String(chars, 0, len);
    }

    /**
     * 运行之前 indexer 指向起始索引
     * 运行完成之后 indexer 指向下一个非空白字符索引或字符串长度
     * 返回当前非空白字符或 -1
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    final static int skipWhitespace(char[] chars, IntAccessor indexer, final int len) {
        int index = indexer.get();
        int ch = 0;
        while (index < len && isWhitespace(ch = chars[index++])) {
        }
        indexer.set(index);
        return ch;
    }

    /*
     * -------------------------------------------------------
     * assertions
     * -------------------------------------------------------
     */

    final static <T> T throwErr(char[] chars, IntAccessor indexer) {
        int amount = 8, len = chars.length, index = indexer.get();
        int end = index + amount < len ? index + amount : len;
        int start = index < amount ? 0 : index - amount;
        throw new IllegalArgumentException(
            new StringBuilder((amount + 5) * 2)
                .append(">>>>>")
                .append(chars, start, end - start)
                .append("<<<<<").toString()
        );
    }

    final static void assertNull(Object value, char[] chars, IntAccessor indexer) {
        if (value == null) {
            return;
        }
        throwErr(chars, indexer);
    }


    final static void assertNonNull(Object value, char[] chars, IntAccessor indexer) {
        if (value == null) {
            throwErr(chars, indexer);
        }
    }

    final static void assertTrue(boolean value, char[] chars, IntAccessor indexer) {
        if (value) {
            return;
        }
        throwErr(chars, indexer);
    }

    final static void assertFalse(boolean value, char[] chars, IntAccessor indexer) {
        if (value) {
            throwErr(chars, indexer);
        }
    }

    /*
     * -----------------------------------------------
     * api
     * -----------------------------------------------
     */

    protected final static Object run0(String expression, Object data) {
        return ParseCore.parse(expression).use(data);
    }

    protected final static Object parseRun0(String expression, String[] delimiters, Object data) {
        return ParseDelimiters.parse(expression, delimiters).use(data);
    }
}

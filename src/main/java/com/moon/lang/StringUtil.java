package com.moon.lang;

import com.moon.enums.ArraysEnum;
import com.moon.enums.Const;
import com.moon.enums.PredicateEnum;
import com.moon.io.IOUtil;
import com.moon.lang.support.StringSupport;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.function.Function;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.support.StringSupport.concatHandler;

/**
 * @author benshaoye
 * @date 2018-09-11
 */
public final class StringUtil {
    private StringUtil() {
        noInstanceError();
    }

    private static final String NULL_STRING = "null";
    public final static String EMPTY = "";
    private final static char[] EMPTY_CHARS = ArraysEnum.CHARS.empty();
    private static final String NULL = null;
    private final static int NO = -1;

    /**
     * 比较两个字符序列是否相同
     *
     * @param str1
     * @param str2
     * @return
     */
    public static <T extends CharSequence> boolean equals(T str1, T str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 比较两个字符序列内容是否一致
     * <p>
     * "aaa".equals("aaa") ----------------------------------------> true
     * new String("1").equals(new String("1")) --------------------> true
     * StringUtil.equals(new String("1"), new String("1")) --------> true
     * StringUtil.contentEquals(new String("1"), new String("1")) -> true
     * <p>
     * new StringBuffer("1").equals(new StringBuffer("1")) --------------------> false
     * StringUtil.equals(new StringBuffer("1"), new StringBuffer("1")) --------> false
     * StringUtil.contentEquals(new StringBuffer("1"), new StringBuffer("1")) -> true
     * <p>
     * new StringBuilder("1").equals(new StringBuilder("1")) --------------------> false
     * StringUtil.equals(new StringBuilder("1"), new StringBuilder("1")) --------> false
     * StringUtil.contentEquals(new StringBuilder("1"), new StringBuilder("1")) -> true
     * <p>
     * StringUtil.contentEquals(new StringBuffer("1"), new StringBuilder("1")) --> false
     *
     * @param str1
     * @param str2
     * @return
     */
    public static <T extends CharSequence> boolean contentEquals(T str1, T str2) {
        return str1 == null ? str2 == null : (str1.equals(str2) || StringSupport.matches(str1, str2));
    }

    /*
     * -------------------------------------------------------------------
     * indexOf
     * -------------------------------------------------------------------
     */

    public static int indexOf(String str, boolean bool) {
        return str == null ? NO : str.indexOf(String.valueOf(bool));
    }

    public static int indexOf(String str, double num) {
        return str == null ? NO : str.indexOf(String.valueOf(num));
    }

    public static int indexOf(String str, float num) {
        return str == null ? NO : str.indexOf(String.valueOf(num));
    }

    public static int indexOf(String str, int num) {
        return str == null ? NO : str.indexOf(String.valueOf(num));
    }

    public static int indexOf(String str, long num) {
        return str == null ? NO : str.indexOf(String.valueOf(num));
    }

    public static int indexOf(String str, char c) {
        return str == null ? NO : str.indexOf(c);
    }

    public static int indexOf(String str, String test) {
        return str == null ? NO : str.indexOf(test);
    }

    /*
     * -------------------------------------------------------------------
     * concat
     * -------------------------------------------------------------------
     */

    /**
     * 经一定量测试后，程序中运行的每个字符串长度平均值约在 6 - 14 之间，取 11 作为默认值，一定程度提高效率
     *
     * @param css
     * @return
     */
    public static String concat(CharSequence... css) {
        return concatHandler(PredicateEnum.TRUE, css);
    }

    public static String concatSkipNulls(CharSequence... css) {
        return concatHandler(PredicateEnum.NON_NULL, css);
    }

    public static String concatSkipBlanks(CharSequence... css) {
        return concatHandler(cs -> isNotBlank(cs), css);
    }

    public static String concatSkipEmpties(CharSequence... css) {
        return concatHandler(cs -> isNotEmpty(cs), css);
    }

    public static String concatUseForNulls(CharSequence nullVal, CharSequence... css) {
        return map(str -> (str == null ? nullVal : str), css);
    }

    /*
     * -------------------------------------------------------------------
     * assertions
     * -------------------------------------------------------------------
     */

    /**
     * @param string
     * @return
     * @see #isEmpty(CharSequence)
     */
    public static boolean isNotEmpty(CharSequence string) {
        return !isEmpty(string);
    }

    /**
     * string is null、""(empty string)
     * <p>
     * StringUtil.isEmpty(null)         ==> true
     * StringUtil.isEmpty("")           ==> true
     * <p>
     * StringUtil.isEmpty("null")       ==> false
     * StringUtil.isEmpty("undefined")  ==> false
     * StringUtil.isEmpty(" ")          ==> false
     * StringUtil.isEmpty("a")          ==> false
     * StringUtil.isEmpty("abc")        ==> false
     * StringUtil.isEmpty(" a b c ")    ==> false
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static <C extends CharSequence> C requireEmpty(C c) {
        if (isEmpty(c)) {
            return c;
        }
        throw new IllegalArgumentException("Require an empty String, but got: " + c);
    }

    public static <C extends CharSequence> C requireNotEmpty(C c) {
        if (isEmpty(c)) {
            throw new IllegalArgumentException("Require not empty String, but got: " + c);
        }
        return c;
    }

    /**
     * @param string
     * @return
     * @see #isBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence string) {
        return !isBlank(string);
    }

    /**
     * string is null、""(empty string) or " "(all char is whitespace)
     * <p>
     * StringUtil.isBlank(null)         ==> true
     * StringUtil.isBlank("")           ==> true
     * StringUtil.isBlank(" ")          ==> true
     * <p>
     * StringUtil.isBlank("null")       ==> false
     * StringUtil.isBlank("undefined")  ==> false
     * StringUtil.isBlank("a")          ==> false
     * StringUtil.isBlank("abc")        ==> false
     * StringUtil.isBlank(" a b c ")    ==> false
     *
     * @param string
     * @return
     */
    public static boolean isBlank(CharSequence string) {
        if (string != null) {
            for (int i = 0, len = string.length(); i < len; i++) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <C extends CharSequence> C requireBlank(C c) {
        if (isBlank(c)) {
            return c;
        }
        throw new IllegalArgumentException("Require an blank String, but got: " + c);
    }

    public static <C extends CharSequence> C requireNotBlank(C c) {
        if (isBlank(c)) {
            throw new IllegalArgumentException("Require not blank String, but got: " + c);
        }
        return c;
    }

    /**
     * cs is null or "null"
     * <p>
     * StringUtil.isNullString(null)         ==> true
     * StringUtil.isNullString("null")       ==> true
     * <p>
     * StringUtil.isNullString("undefined")  ==> false
     * StringUtil.isNullString("")           ==> false
     * StringUtil.isNullString(" ")          ==> false
     * StringUtil.isNullString("a")          ==> false
     * StringUtil.isNullString("abc")        ==> false
     * StringUtil.isNullString(" a b c ")    ==> false
     *
     * @param cs
     * @return
     */
    public static boolean isNullString(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        if (cs instanceof StringBuffer) {
            return "null".equals(cs.toString());
        }
        if (cs.length() == 4) {
            return cs.charAt(0) == 'n'
                && cs.charAt(1) == 'u'
                && cs.charAt(2) == 'l'
                && cs.charAt(3) == 'l';
        }
        return false;
    }

    /**
     * cs is null、"null" or "undefined"
     * <p>
     * StringUtil.isUndefined(null)         ==> true
     * StringUtil.isUndefined("null")       ==> true
     * StringUtil.isUndefined("undefined")  ==> true
     * <p>
     * StringUtil.isUndefined("")           ==> false
     * StringUtil.isUndefined(" ")          ==> false
     * StringUtil.isUndefined("a")          ==> false
     * StringUtil.isUndefined("abc")        ==> false
     * StringUtil.isUndefined(" a b c ")    ==> false
     *
     * @param cs
     * @return
     */
    public static boolean isUndefined(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        if (cs instanceof StringBuffer) {
            String str = cs.toString();
            return "null".equals(str) || "undefined".equals(str);
        }
        switch (cs.length()) {
            case 4:
                return cs.charAt(0) == 'n'
                    && cs.charAt(1) == 'u'
                    && cs.charAt(2) == 'l'
                    && cs.charAt(3) == 'l';
            case 9:
                return cs.charAt(0) == 'u'
                    && cs.charAt(1) == 'n'
                    && cs.charAt(2) == 'd'
                    && cs.charAt(3) == 'e'
                    && cs.charAt(4) == 'f'
                    && cs.charAt(5) == 'i'
                    && cs.charAt(6) == 'n'
                    && cs.charAt(7) == 'e'
                    && cs.charAt(8) == 'd';
            default:
                return false;
        }
    }

    /*
     * -------------------------------------------------------------------
     * default
     * -------------------------------------------------------------------
     */

    public static String emptyIfNull(String str) {
        return str == null ? EMPTY : str;
    }

    public static StringBuffer emptyIfNull(StringBuffer str) {
        return str == null ? new StringBuffer() : str;
    }

    public static StringBuilder emptyIfNull(StringBuilder str) {
        return str == null ? new StringBuilder() : str;
    }

    public static <T extends CharSequence> T nullIfEmpty(T cs) {
        return isEmpty(cs) ? null : cs;
    }

    public static <T extends CharSequence> T defaultIfNull(T cs, T defaultValue) {
        return cs == null ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T cs, T defaultValue) {
        return isEmpty(cs) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfBlank(T cs, T defaultValue) {
        return isBlank(cs) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfEquals(T cs, T testString, T defaultValue) {
        return equals(cs, testString) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfNotEquals(T cs, T testString, T defaultValue) {
        return equals(cs, testString) ? cs : defaultValue;
    }

    public static <T extends CharSequence> T defaultIfContains(T cs, T searchString, T defaultValue) {
        return contains(cs, searchString) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfNotContains(T cs, T searchString, T defaultValue) {
        return contains(cs, searchString) ? cs : defaultValue;
    }


    /**
     * 以下几个方法主要是针对 JavaScript
     *
     * @param cs
     * @param defaultValue
     * @return
     */
    public static <T extends CharSequence> T defaultIfNullString(T cs, T defaultValue) {
        return isNullString(cs) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T defaultIfUndefined(T cs, T defaultValue) {
        return isUndefined(cs) ? defaultValue : cs;
    }

    public static <T extends CharSequence> T nullIfNullString(T cs) {
        return defaultIfNullString(cs, null);
    }

    public static String emptyIfNullString(String cs) {
        return defaultIfNullString(cs, EMPTY);
    }

    public static <T extends CharSequence> T nullIfUndefined(T cs) {
        return defaultIfUndefined(cs, null);
    }

    public static String emptyIfUndefined(String cs) {
        return defaultIfUndefined(cs, EMPTY);
    }

    public static <T extends CharSequence> boolean contains(T cs, T search) {
        if (equals(cs, search)) {
            return true;
        } else if (cs != null && search == null) {
            return false;
        } else if (indexOf(cs.toString(), search.toString()) < 0) {
            return false;
        }
        return true;
    }

    /*
     * -------------------------------------------------------------------
     * length
     * -------------------------------------------------------------------
     */

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /*
     * -------------------------------------------------------------------
     * pad to length
     * -------------------------------------------------------------------
     */

    public static String padStart(Object source, char ch, int length) {
        String src = String.valueOf(source);
        int diff = length - length(src);
        if (diff > 0) {
            char[] chars = new char[diff];
            Arrays.fill(chars, ch);
            return String.valueOf(chars) + source;
        }
        return src;
    }

    public static String padEnd(Object source, char ch, int length) {
        String src = String.valueOf(source);
        int diff = length - length(src);
        if (diff > 0) {
            char[] chars = new char[diff];
            Arrays.fill(chars, ch);
            return source + String.valueOf(chars);
        }
        return src;
    }

    /*
     * -------------------------------------------------------------------
     * map
     * -------------------------------------------------------------------
     */

    public static <T, S extends CharSequence> String map(Function<T, S> function, T... objects) {
        final int length = objects.length;
        if (length > 0) {
            StringBuilder builder = new StringBuilder(length * Const.DEFAULT_LENGTH);
            for (int i = 0; i < length; i++) {
                builder.append(function.apply(objects[i]));
            }
            return builder.toString();
        }
        return EMPTY;
    }

    /*
     * -------------------------------------------------------------------
     * toString
     * -------------------------------------------------------------------
     */

    public static String toString(CharSequence cs) {
        return cs == null ? NULL_STRING : cs.toString();
    }

    public static final String toString(char[] chars) {
        return chars == null ? NULL_STRING : new String(chars, 0, chars.length);
    }

    public static final String toString(char[] chars, int from, int end) {
        return chars == null ? NULL_STRING : new String(chars, from, end);
    }

    public static final StringBuilder toBuilder(char[] chars, int from, int to) {
        StringSupport.checkIndexesBetween(from, to, chars.length);
        int l = to - from;
        StringBuilder builder = new StringBuilder(l);
        builder.append(chars, from, to);
        return builder;
    }

    public static final StringBuffer toBuffer(char[] chars, int from, int to) {
        StringSupport.checkIndexesBetween(from, to, chars.length);
        int l = to - from;
        StringBuffer builder = new StringBuffer(l);
        builder.append(chars, from, to);
        return builder;
    }


    public static String toString(Object value) {
        if (value == null) {
            return NULL_STRING;
        }
        Class clazz = value.getClass();
        if (clazz.isArray()) {
            return ArraysEnum.getOrObjects(clazz).stringify(value);
        } else if (value instanceof File) {
            return IOUtil.toString((File) value);
        } else if (value instanceof InputStream) {
            return IOUtil.toString((InputStream) value);
        } else if (value instanceof Reader) {
            return IOUtil.toString((Reader) value);
        }
        return value.toString();
    }

    /*
     * -------------------------------------------------------------------
     * repeat
     * -------------------------------------------------------------------
     */

    public static String repeat(CharSequence cs, int count) {
        if (cs != null) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (count == 0) {
                return EMPTY;
            }

            String str = cs.toString();
            int len = str.length();
            if (len == 0) {
                return EMPTY;
            }

            long calcLen = (long) len * (long) count;
            int newLen = (int) calcLen;
            if (newLen != calcLen) {
                throw new StringIndexOutOfBoundsException(String.valueOf(calcLen));
            }

            char[] data = new char[newLen];
            str.getChars(0, len, data, 0);

            int begin;
            for (begin = len; begin < newLen - begin; begin <<= 1) {
                System.arraycopy(data, 0, data, begin, begin);
            }
            System.arraycopy(data, 0, data, begin, newLen - begin);
            return String.valueOf(data);
        }
        return null;
    }

    /*
     * -------------------------------------------------------------------
     * trim
     * -------------------------------------------------------------------
     */

    public static String trim(CharSequence string) {
        return trimToEmpty(string);
    }

    public static String trimToNull(CharSequence string) {
        return trimToDefault(string, NULL);
    }

    public static String trimToEmpty(CharSequence string) {
        return trimToDefault(string, EMPTY);
    }

    public static String trimToDefault(CharSequence cs, String defaultValue) {
        String ret = isEmpty(cs) ? defaultValue : cs.toString().trim();
        return ret == null ? ret : (length(ret) > 0 ? (ret.charAt(0) == 65279 ? ret.substring(1) : ret) : EMPTY);
    }

    /*
     * -------------------------------------------------------------------
     * operations
     * -------------------------------------------------------------------
     */

    /**
     * 删除{@link String}、{@link StringBuilder}、{@link StringBuffer}中的空白字符
     * 不支持其他类型{@link CharSequence}
     *
     * @param cs
     * @param <T>
     * @return
     * @throws IllegalArgumentException can not support except types in String、StringBuilder、StringBuffer
     */
    public static <T extends CharSequence> T deleteWhitespaces(T cs) {
        if (cs != null) {
            int length = cs.length(), curr = 0;
            char[] chars;
            if (length > 0) {
                char ch;
                int i = 0;
                chars = new char[length];
                for (; i < length; i++) {
                    if (!Character.isWhitespace(ch = cs.charAt(i)) && ch != 65279) {
                        chars[curr++] = ch;
                    }
                }
            } else {
                chars = ArraysEnum.CHARS.empty();
            }
            if (cs instanceof String) {
                return (T) (curr == 0 ? EMPTY : String.valueOf(chars, 0, curr));
            }
            if (cs instanceof StringBuilder) {
                return (T) new StringBuilder(curr).append(chars, 0, curr);
            }
            if (cs instanceof StringBuffer) {
                return (T) new StringBuffer(curr).append(chars, 0, curr);
            }
            throw new IllegalArgumentException(cs.toString());
        }
        return null;
    }

    public final static String onlyWhitespace(String str) {
        char[] chars = toCharArray(str), value = ArraysEnum.CHARS.empty();
        boolean isNotWhitespace = true;
        int i = 0, idx = 0, len = chars.length;
        char curr;
        for (; i < len; i++) {
            curr = chars[i];
            if (Character.isWhitespace(curr)) {
                if (isNotWhitespace) {
                    isNotWhitespace = false;
                    value = SupportUtil.setChar(value, idx++, curr);
                }
            } else {
                isNotWhitespace = true;
                value = SupportUtil.setChar(value, idx++, curr);
            }
        }
        return SupportUtil.toStr(value, idx);
    }

    /**
     * 去掉字符串中重复字符
     *
     * @param sourceString
     */
    public static final String distinctChars(String sourceString) {
        if (sourceString != null) {
            int len = sourceString.length();
            if (len == 0) {
                return EMPTY;
            }

            char[] chars = null;
            int i = 0, index = 0, j;
            outer:
            for (; i < len; i++) {
                char ch = sourceString.charAt(i);
                if (chars == null) {
                    chars = SupportUtil.setChar(chars, index++, ch);
                } else {
                    for (j = 0; j < index; j++) {
                        if (chars[j] == ch) {
                            continue outer;
                        }
                    }
                    chars = SupportUtil.setChar(chars, index++, ch);
                }
            }
            return String.valueOf(chars, 0, index);
        }
        return null;
    }

    /*
     * -------------------------------------------------------------------
     * formatter
     * -------------------------------------------------------------------
     */

    public static final char[] formatToChars(String template, Object... values) {
        return StringSupport.format0((end, chars) -> Arrays.copyOfRange(chars, 0, end), template, values);
    }

    public static final StringBuilder formatToBuilder(String template, Object... values) {
        return StringSupport.format0((end, chars) -> toBuilder(chars, 0, end), template, values);
    }

    public static final StringBuffer formatToBuffer(String template, Object... values) {
        return StringSupport.format0((end, chars) -> toBuffer(chars, 0, end), template, values);
    }

    public static final String format(String template, Object... values) {
        return StringSupport.format0((end, chars) -> new String(chars, 0, end), template, values);
    }

    public static final StringBuilder format(StringBuilder builder, Object... values) {
        return StringSupport.format0((end, chars) -> toBuilder(chars, 0, end), toCharArray(builder), values);
    }

    /*
     * -------------------------------------------------------------------
     * to char array
     * -------------------------------------------------------------------
     */

    public static final char[] toCharArray(String str) {
        return str == null ? null : str.toCharArray();
    }

    public static final char[] toCharArray(String str, boolean emptyIfNull) {
        return str == null ? (emptyIfNull ? EMPTY_CHARS : null) : str.toCharArray();
    }

    public static final char[] toCharArray(StringBuffer builder) {
        return toCharArray(builder, false);
    }

    public static final char[] toCharArray(StringBuffer buffer, boolean emptyIfNull) {
        if (buffer != null) {
            int len = buffer.length();
            char[] template = new char[len];
            buffer.getChars(0, len, template, 0);
            return template;
        } else {
            return emptyIfNull ? EMPTY_CHARS : null;
        }
    }

    public static final char[] toCharArray(StringBuilder builder) {
        return toCharArray(builder, false);
    }

    public static final char[] toCharArray(StringBuilder builder, boolean emptyIfNull) {
        if (builder == null) {
            return emptyIfNull ? EMPTY_CHARS : null;
        } else {
            int len = builder.length();
            char[] template = new char[len];
            builder.getChars(0, len, template, 0);
            return template;
        }
    }

    /*
     * -------------------------------------------------------------------
     * capitalize
     * -------------------------------------------------------------------
     */

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static final String capitalize(String str) {
        int len = str == null ? 0 : str.length();
        if (len == 0) {
            return str;
        }
        char ch = str.charAt(0);
        if (CharUtil.isLowerCase(ch)) {
            char[] chars = new char[len];
            chars[0] = (char) (ch - 32);
            str.getChars(1, len, chars, 1);
            return new String(chars);
        }
        return str;
    }
}

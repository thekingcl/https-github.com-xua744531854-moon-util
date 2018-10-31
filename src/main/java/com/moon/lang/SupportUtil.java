package com.moon.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class SupportUtil {

    private SupportUtil() {
        noInstanceError("这个类不推荐任何形式调用");
    }

    public static int addTrueValue(char[] data, int posBegin) {
        data[posBegin++] = 't';
        data[posBegin++] = 'r';
        data[posBegin++] = 'u';
        data[posBegin++] = 'e';
        return posBegin;
    }

    public static int addFalseValue(char[] data, int posBegin) {
        data[posBegin++] = 'f';
        data[posBegin++] = 'a';
        data[posBegin++] = 'l';
        data[posBegin++] = 's';
        data[posBegin++] = 'e';
        return posBegin;
    }

    public static int addBooleanValue(char[] data, boolean bool, int posBegin) {
        return bool ? addTrueValue(data, posBegin) : addFalseValue(data, posBegin);
    }

    public static int addNullValue(char[] data, int posBegin) {
        data[posBegin++] = 'n';
        data[posBegin++] = 'u';
        data[posBegin++] = 'l';
        data[posBegin++] = 'l';
        return posBegin;
    }

    public static int addUndefinedValue(char[] data, int posBegin) {
        data[posBegin++] = 'u';
        data[posBegin++] = 'n';
        data[posBegin++] = 'd';
        data[posBegin++] = 'e';
        data[posBegin++] = 'f';
        data[posBegin++] = 'i';
        data[posBegin++] = 'n';
        data[posBegin++] = 'e';
        data[posBegin++] = 'd';
        return posBegin;
    }

    /**
     * 输入对象必须是一个数组、集合或 Map
     *
     * @param o
     * @return
     */
    public static Object firstItem(Object o) {
        if (o == null) {
            return null;
        }
        if (o.getClass().isArray()) {
            return Array.get(o, 0);
        }
        if (o instanceof Collection) {
            Collection collection = (Collection) o;
            return collection.iterator().next();
        }
        if (o instanceof Map) {
            Map map = (Map) o;
            return map.values().iterator().next();
        }
        throw new IllegalArgumentException();
    }

    /**
     * 当数组或集合只有一项时，返回第一项，否则返回数组长度或集合 size
     * 其他自定义对象或集合均抛出异常
     *
     * @param o
     * @return
     */
    public static Object onlyOneItemOrSize(Object o) {
        if (o == null) {
            return null;
        }
        int size;
        if (o.getClass().isArray()) {
            return ((size = Array.getLength(o)) == 1) ? Array.get(o, 0) : size;
        }
        if (o instanceof Collection) {
            return (size = ((Collection) o).size()) == 1 ? ((Collection) o).iterator().next() : size;
        }
        if (o instanceof Map) {
            return (size = ((Map) o).size()) == 1 ? ((Map) o).values().iterator().next() : size;
        }
        throw new IllegalArgumentException();
    }

    public static void validateNumberArray(Object array) {
        if (array == null) {
            throw new NullPointerException("The array must not be null");
        } else if (Array.getLength(array) == 0) {
            throw new ArrayIndexOutOfBoundsException("The array must not be Empty, get out of range: 0");
        }
    }

    public static <T> T matchOne(Collection<T> collection, Predicate<T> test) {
        if (collection != null) {
            for (T t : collection) {
                if (test.test(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static char[] setChar(char[] chars, int index, char ch) {
        chars = defaultChars(chars);
        int len = chars.length;
        if (index >= len) {
            char[] newArr = new char[Math.max(len << 1, 8)];
            System.arraycopy(chars, 0, newArr, 0, len);
            chars = newArr;
        }
        chars[index] = ch;
        return chars;
    }

    public static char[] safeGetChars(char[] chars, int index, String str) {
        chars = defaultChars(chars);
        int strLen = str.length();
        int charsL = chars.length;
        int endIdx = strLen + index;
        if (strLen + index > charsL) {
            char[] newArr = new char[endIdx + 8];
            System.arraycopy(chars, 0, newArr, 0, charsL);
            chars = newArr;
        }
        str.getChars(0, strLen, chars, index);
        return chars;
    }

    public static char[] ensureToLength(char[] chars, int newLength, int presentLength) {
        if (chars == null) {
            return new char[newLength];
        }
        if (chars.length < newLength) {
            char[] newArr = new char[newLength];
            System.arraycopy(chars, 0, newArr, 0, presentLength);
            return newArr;
        }
        return chars;
    }

    public static char[] defaultChars(char[] chars) {
        return chars == null ? new char[8] : chars;
    }
}

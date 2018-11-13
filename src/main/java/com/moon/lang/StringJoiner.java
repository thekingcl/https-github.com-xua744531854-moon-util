package com.moon.lang;

import com.moon.enums.ArraysEnum;
import com.moon.enums.Const;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.moon.lang.SupportUtil.ensureToLength;
import static com.moon.lang.SupportUtil.safeGetChars;
import static com.moon.util.IteratorUtil.forEach;
import static java.lang.System.arraycopy;

/**
 * @author benshaoye
 */
public class StringJoiner {

    private final static int DFT_LEN = 16;
    public final static String EMPTY = Const.EMPTY;

    private String delimiter;
    private String prefix;
    private String suffix;

    private int itemCount;
    private boolean skipNulls;
    private boolean requireNonNull;
    private String defaultIfNull = String.valueOf(delimiter);

    private char[] value;
    private int length;

    private Function<Object, String> stringer;

    /*
     * 构造函数
     */

    public StringJoiner(CharSequence delimiter) {
        this(delimiter, null, null);
    }

    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this.delimiter = emptyIfNull(delimiter);
        this.setPrefix(prefix).setSuffix(suffix).setStringer().reset();
    }

    private String emptyIfNull(CharSequence seq) {
        return seq == null || seq.length() == 0 ? EMPTY : seq.toString();
    }

    public static StringJoiner of(CharSequence delimiter) {
        return new StringJoiner(delimiter);
    }

    public static StringJoiner of(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return new StringJoiner(delimiter, prefix, suffix);
    }

    /*
     * add
     */

    private StringJoiner addStr(String sequence) {
        if (sequence == null) {
            return this;
        }
        int length = this.length;
        int itemCount = this.itemCount;
        char[] val = this.value;

        if (itemCount == 0 && prefix != EMPTY) {
            val = safeGetChars(val, length, prefix);
            length = prefix.length();
        }

        if (itemCount > 0) {
            length = addDelimiterToLen();
            val = this.value;
        }

        this.value = safeGetChars(val, length, sequence);
        this.length = length + sequence.length();
        this.itemCount = itemCount + 1;
        return this;
    }

    public StringJoiner add(CharSequence sequence) {
        return addStr(testAndGetString(sequence));
    }

    public StringJoiner add(Object obj) {
        return addStr(testAndGetString(obj));
    }

    /*
     * join
     */

    public <T> StringJoiner join(T[] arr) {
        testRequiredNonNull(arr);
        if (arr != null) {
            char[] chars = this.value;
            int len = arr.length * DFT_LEN + length;
            if (len > chars.length) {
                this.value = ensureToLength(chars, len + 8, length);
            }
            forEach(arr, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(boolean[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(char[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(byte[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(short[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(int[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(long[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(float[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(double[] values) {
        testRequiredNonNull(values);
        if (values != null) {
            this.value = ensure(values.length * 5 + length);
            forEach(values, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(Collection<T> arr) {
        testRequiredNonNull(arr);
        if (arr != null) {
            char[] chars = this.value;
            int len = arr.size() * DFT_LEN + length;
            if (len > chars.length) {
                this.value = ensureToLength(chars, len + 8, length);
            }
            forEach(arr, item -> addStr(testAndGetString(item)));
        }
        return this;
    }

    public <T> StringJoiner join(Iterator<T> arr) {
        testRequiredNonNull(arr);
        forEach(arr, item -> addStr(testAndGetString(item)));
        return this;
    }

    public <T> StringJoiner join(Iterable<T> arr) {
        testRequiredNonNull(arr);
        forEach(arr, item -> addStr(testAndGetString(item)));
        return this;
    }

    public <T> StringJoiner join(Enumeration<T> arr) {
        testRequiredNonNull(arr);
        forEach(arr, item -> addStr(testAndGetString(item)));
        return this;
    }

    public <K, V> StringJoiner join(Map<K, V> map, BiFunction<K, V, String> converter) {
        testRequiredNonNull(map);
        // (key, value) -> addStr(testAndGetString(converter.apply(key, value)))
        forEach(map, (k, v) -> converter.apply(k, v));
        return this;
    }

    /*
     * merge
     */

    public StringJoiner merge(StringJoiner joiner) {
        return addStr(testAndGetString(joiner));
    }

    public StringJoiner merge(java.util.StringJoiner joiner) {
        return addStr(testAndGetString(joiner));
    }

    /*
     * defaults
     */

    public StringJoiner reset() {
        this.value = ArraysEnum.CHARS.empty();
        this.itemCount = 0;
        return this;
    }

    public StringJoiner setStringer(Function<Object, String> stringer) {
        this.stringer = Objects.requireNonNull(stringer);
        return this;
    }

    public StringJoiner setStringer() {
        return this.setStringer(String::valueOf);
    }

    public StringJoiner setPrefix(CharSequence prefix) {
        this.prefix = emptyIfNull(prefix);
        return this;
    }

    public StringJoiner setSuffix(CharSequence suffix) {
        this.suffix = emptyIfNull(suffix);
        return this;
    }

    public StringJoiner skipNulls() {
        return skipNulls(true);
    }

    public StringJoiner skipNulls(boolean skipNulls) {
        this.skipNulls = skipNulls;
        return this;
    }

    public StringJoiner requireNonNull(boolean requireNonNull) {
        this.requireNonNull = requireNonNull;
        return this;
    }

    public StringJoiner useForNull(CharSequence sequence) {
        this.defaultIfNull = String.valueOf(sequence);
        return this;
    }

    public StringJoiner setDelimiter(CharSequence delimiter) {
        this.delimiter = emptyIfNull(delimiter);
        return this;
    }

    private boolean justDoIt;

    private int addDelimiterToLen() {
        if (justDoIt) {
            justDoIt = false;
            return length;
        }
        String del = delimiter;
        if (del != EMPTY) {
            int len = this.length;
            this.value = safeGetChars(this.value, len, del);
            len += del.length();
            this.length = len;
            return len;
        }
        return length;
    }

    public StringJoiner addDelimiter() {
        justDoIt = false;
        addDelimiterToLen();
        justDoIt = true;
        return this;
    }

    /*
     * overrides
     */

    public <A extends Appendable> A appendTo(A apender) {
        Objects.requireNonNull(apender);
        try {
            apender.append(toString());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return apender;
    }

    @Override
    public String toString() {
        char[] chars = this.value;
        if (suffix != EMPTY) {
            if (chars == null) {
                return suffix;
            }
            int sufLef = suffix.length();
            int length = this.length;
            char[] result = new char[length + sufLef];
            arraycopy(chars, 0, result, 0, length);
            result = safeGetChars(result, length, suffix);
            return new String(result);
        }
        if (chars == null) {
            return EMPTY;
        }
        return new String(chars, 0, length);
    }

    public int length() {
        return this.length + this.suffix.length();
    }

    /*
     * tools
     */

    private char[] ensure(int newLength) {
        return ensureToLength(this.value, newLength, length);
    }

    private String testAndGetString(Object o) {
        o = testRequiredNonNull(o);
        return o == null ? (skipNulls ? null : defaultIfNull) : stringer.apply(o);
    }

    private <T> T testRequiredNonNull(T o) {
        if (requireNonNull && o == null) {
            throw new NullPointerException();
        }
        return o;
    }
}

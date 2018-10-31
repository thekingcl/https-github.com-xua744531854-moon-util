package com.moon.lang;

import com.moon.enums.Const;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;

import static com.moon.lang.SupportUtil.ensureToLength;
import static com.moon.lang.SupportUtil.safeGetChars;
import static com.moon.util.IteratorUtil.forEach;
import static java.lang.System.arraycopy;
import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
public class StringJoiner {

    private final static int DFT_LEN = 16;
    public final static String EMPTY = Const.EMPTY;

    private String delimiter;
    private final String prefix;
    private final String suffix;

    private int itemCount;
    private boolean skipNulls;
    private boolean requireNonNull;
    private String defaultIfNull = String.valueOf(delimiter);

    private char[] value;
    private int length;

    /*
     * 构造函数
     */

    public StringJoiner(CharSequence delimiter) {
        this(delimiter, null, null);
    }

    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this.delimiter = emptyIfNull(delimiter);
        this.prefix = emptyIfNull(prefix);
        this.suffix = emptyIfNull(suffix);
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

        val = safeGetChars(val, length, sequence);
        length += sequence.length();
        itemCount++;

        this.value = val;
        this.length = length;
        this.itemCount = itemCount;
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

    /*
     * tools
     */

    private String testAndGetString(Object o) {
        o = testRequiredNonNull(o);
        return o == null ? (skipNulls ? null : defaultIfNull) : String.valueOf(o);
    }

    private <T> T testRequiredNonNull(T o) {
        if (requireNonNull && o == null) {
            throw new NullPointerException();
        }
        return o;
    }
}

package com.moon.lang.ref;

import java.util.function.Supplier;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class AccessorUtil {
    private AccessorUtil() {
        noInstanceError();
    }

    public final static BooleanAccessor ofTrue() {
        return BooleanAccessor.ofTrue();
    }

    public final static BooleanAccessor ofFalse() {
        return BooleanAccessor.ofFalse();
    }

    public final static BooleanAccessor ofBoolean(boolean value) {
        return BooleanAccessor.of(value);
    }

    public final static <T> FinalAccessor<T> ofFinal() {
        return FinalAccessor.of();
    }

    public final static <T> FinalAccessor<T> ofFinal(T value) {
        return FinalAccessor.of(value);
    }

    public final static <T> WeakAccessor<T> ofWeak(Supplier<T> supplier) {
        return WeakAccessor.of(supplier);
    }

    public final static IntAccessor ofInt() {
        return IntAccessor.of();
    }

    public final static IntAccessor ofInt(int value) {
        return IntAccessor.of(value);
    }

    public final static LongAccessor ofLong() {
        return LongAccessor.of();
    }

    public final static LongAccessor ofLong(long value) {
        return LongAccessor.of(value);
    }

    public final static DoubleAccessor ofDouble() {
        return DoubleAccessor.of();
    }

    public final static DoubleAccessor ofDouble(double value) {
        return DoubleAccessor.of(value);
    }
}

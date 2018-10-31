package com.moon.lang.ref;

import com.moon.lang.Executable;
import com.moon.lang.ThrowUtil;
import com.moon.util.function.BooleanConsumer;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static com.moon.lang.ThrowUtil.throwRuntime;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
public class BooleanAccessor {

    private boolean value;

    public BooleanAccessor(boolean value) {
        this.set(value);
    }

    /*
     * ----------------------------------------------------------------------------
     * static constructor
     * ----------------------------------------------------------------------------
     */

    public static BooleanAccessor ofFalse() {
        return of(false);
    }

    public static BooleanAccessor ofTrue() {
        return of(true);
    }

    public static BooleanAccessor of(boolean value) {
        return new BooleanAccessor(value);
    }

    /*
     * ----------------------------------------------------------------------------
     * sets
     * ----------------------------------------------------------------------------
     */

    public BooleanAccessor set(boolean value) {
        this.value = value;
        return this;
    }

    public BooleanAccessor set(BooleanSupplier supplier) {
        return set(supplier.getAsBoolean());
    }

    public BooleanAccessor setTrue() {
        return set(true);
    }

    public BooleanAccessor setFalse() {
        return set(false);
    }

    public BooleanAccessor flip() {
        return set(!value);
    }

    /*
     * ----------------------------------------------------------------------------
     * assertions
     * ----------------------------------------------------------------------------
     */

    public boolean isTrue() {
        return value;
    }

    public boolean isFalse() {
        return !value;
    }

    /*
     * ----------------------------------------------------------------------------
     * defaultExecutor
     * ----------------------------------------------------------------------------
     */

    public BooleanAccessor ifTrue(Executable executable) {
        if (isTrue()) {
            executable.execute();
        }
        return this;
    }

    public BooleanAccessor ifFalse(Executable executable) {
        if (isFalse()) {
            executable.execute();
        }
        return this;
    }

    public BooleanAccessor ifTrue(BooleanConsumer consumer) {
        if (isTrue()) {
            consumer.accept(true);
        }
        return this;
    }

    public BooleanAccessor ifFalse(BooleanConsumer consumer) {
        if (isFalse()) {
            consumer.accept(false);
        }
        return this;
    }

    public BooleanAccessor use(BooleanConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    /*
     * ----------------------------------------------------------------------------
     * execute by condition
     * ----------------------------------------------------------------------------
     */

    public <T> T ifTrueOrNull(Supplier<T> supplier) {
        return isTrue() ? supplier.get() : null;
    }

    public <T> T ifFalseOrNull(Supplier<T> supplier) {
        return isFalse() ? supplier.get() : null;
    }

    public <T> T ifTrueOrThrow(Supplier<T> supplier) {
        return isTrue() ? supplier.get() : throwRuntime(toString());
    }

    public <T> T ifFalseOrThrow(Supplier<T> supplier) {
        return isFalse() ? supplier.get() : throwRuntime(toString());
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}

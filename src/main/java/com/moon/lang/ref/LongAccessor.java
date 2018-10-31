package com.moon.lang.ref;

import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class LongAccessor {

    private long value;

    public LongAccessor() {
    }

    public LongAccessor(long value) {
        this.set(value);
    }

    public static LongAccessor of() {
        return new LongAccessor();
    }

    public static LongAccessor of(long value) {
        return new LongAccessor(value);
    }

    public LongAccessor set(long value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public long get() {
        return value;
    }

    public long getAndAdd() {
        return value++;
    }

    public long addAndGet() {
        return ++value;
    }

    public long getAndAdd(long value) {
        return this.value += value;
    }

    public long addAndGet(long value) {
        long now = this.value;
        this.value += value;
        return now;
    }

    public LongAccessor minus(long value) {
        this.value -= value;
        return this;
    }

    public LongAccessor minus() {
        return minus(1);
    }

    public long minusAndGet() {
        return minus().get();
    }

    public long getAndMinus() {
        long num = get();
        this.minus();
        return num;
    }

    public long minusAndGet(long value) {
        return minus(value).get();
    }

    public long getAndMinus(long value) {
        long num = get();
        this.minus(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public LongAccessor add() {
        return this.add(1);
    }

    public LongAccessor add(int value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public LongAccessor ifEq(int value, LongConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifGt(int value, LongConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifLt(int value, LongConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifGtOrEq(int value, LongConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifLtOrEq(int value, LongConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor use(LongConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public LongAccessor compute(ToLongFunction<Long> computer) {
        return set(computer.applyAsLong(value));
    }

    public <T> T transform(LongFunction<T> transformer) {
        return transformer.apply(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

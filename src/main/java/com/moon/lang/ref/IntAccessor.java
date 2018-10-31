package com.moon.lang.ref;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class IntAccessor {

    private int value;

    public IntAccessor() {
    }

    public IntAccessor(int value) {
        this.set(value);
    }

    public static IntAccessor of() {
        return new IntAccessor();
    }

    public static IntAccessor of(int value) {
        return new IntAccessor(value);
    }

    public IntAccessor set(int value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public int get() {
        return value;
    }

    public int getAndAdd() {
        return value++;
    }

    public int addAndGet() {
        return ++value;
    }

    public int getAndAdd(int value) {
        return this.value += value;
    }

    public int addAndGet(int value) {
        int now = this.value;
        this.value += value;
        return now;
    }

    public IntAccessor minus(int value) {
        this.value -= value;
        return this;
    }

    public IntAccessor minus() {
        return minus(1);
    }

    public int minusAndGet() {
        return minus().get();
    }

    public int getAndMinus() {
        int num = get();
        this.minus();
        return num;
    }

    public int minusAndGet(int value) {
        return minus(value).get();
    }

    public int getAndMinus(int value) {
        int num = get();
        this.minus(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public IntAccessor add() {
        return this.add(1);
    }

    public IntAccessor add(int value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public IntAccessor ifEq(int value, IntConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifGt(int value, IntConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifLt(int value, IntConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifGtOrEq(int value, IntConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifLtOrEq(int value, IntConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor use(IntConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public IntAccessor compute(ToIntFunction<Integer> computer) {
        return set(computer.applyAsInt(value));
    }

    public <T> T transform(IntFunction<T> transformer) {
        return transformer.apply(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

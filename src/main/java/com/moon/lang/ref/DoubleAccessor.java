package com.moon.lang.ref;

import java.util.function.DoubleConsumer;
import java.util.function.ToDoubleFunction;

/**
 * @author benshaoye
 * @date 2018/9/15
 */
public class DoubleAccessor {

    private double value;

    public DoubleAccessor() {
    }

    public DoubleAccessor(double value) {
        this.set(value);
    }

    public static DoubleAccessor of() {
        return new DoubleAccessor();
    }

    public static DoubleAccessor of(double value) {
        return new DoubleAccessor(value);
    }

    public DoubleAccessor set(double value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public double get() {
        return value;
    }

    public double getAndAdd() {
        return value++;
    }

    public double addAndGet() {
        return ++value;
    }

    public double getAndAdd(double value) {
        return this.value += value;
    }

    public double addAndGet(double value) {
        double now = this.value;
        this.value += value;
        return now;
    }

    public DoubleAccessor minus(double value) {
        this.value -= value;
        return this;
    }

    public DoubleAccessor minus() {
        return minus(1);
    }

    public double minusAndGet() {
        return minus().get();
    }

    public double getAndMinus() {
        double num = get();
        this.minus();
        return num;
    }

    public double minusAndGet(double value) {
        return minus(value).get();
    }

    public double getAndMinus(double value) {
        double num = get();
        this.minus(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public DoubleAccessor add() {
        return this.add(1);
    }

    public DoubleAccessor add(double value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public DoubleAccessor ifEq(double value, DoubleConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifGt(double value, DoubleConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifLt(double value, DoubleConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifGtOrEq(double value, DoubleConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifLtOrEq(double value, DoubleConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor use(DoubleConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public DoubleAccessor compute(ToDoubleFunction<Double> computer) {
        return set(computer.applyAsDouble(value));
    }

    public DoubleAccessor transform(ToDoubleFunction<Double> transformer) {
        return of(transformer.applyAsDouble(value));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

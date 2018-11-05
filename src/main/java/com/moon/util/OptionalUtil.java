package com.moon.util;

import com.moon.lang.Executable;

import java.util.function.*;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.ThrowUtil.throwRuntime;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
public final class OptionalUtil {
    private OptionalUtil() {
        noInstanceError();
    }

    /*
     * -----------------------------------------------------------
     * return int value
     * -----------------------------------------------------------
     */

    public static <T> int computeAsIntOrZero(T obj, ToIntFunction<T> function) {
        return computeAsIntOrElse(obj, function, 0);
    }

    public static <T> int computeAsIntOrElse(T obj, ToIntFunction<T> function, int elseVal) {
        return obj == null ? elseVal : function.applyAsInt(obj);
    }

    public static <T> int computeAsIntOrGet(T obj, ToIntFunction<T> function, IntSupplier supplier) {
        return obj == null ? supplier.getAsInt() : function.applyAsInt(obj);
    }

    /*
     * -----------------------------------------------------------
     * return value
     * -----------------------------------------------------------
     */

    public static <T, R> R computeOrNull(T obj, Function<T, R> function) {
        return computeOrElse(obj, function, null);
    }

    public static <T, R> R computeOrElse(T obj, Function<T, R> function, R elseVal) {
        return obj == null ? elseVal : function.apply(obj);
    }

    public static <T, R> R computeOrGet(T obj, Function<T, R> function, Supplier<R> supplier) {
        return obj == null ? supplier.get() : function.apply(obj);
    }

    public static <T, R> R computeOrThrow(T obj, Function<T, R> function) {
        return obj == null ? throwRuntime() : function.apply(obj);
    }

    public static <T, R> R computeOrThrow(T obj, Function<T, R> function, String message) {
        return obj == null ? throwRuntime(message) : function.apply(obj);
    }

    /*
     * -----------------------------------------------------------
     * doesn't has return value
     * -----------------------------------------------------------
     */

    public static <T> void ifPresent(T obj, Consumer<T> consumer) {
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    public static <T, U> void ifPresent(T obj, BiConsumer<T, U> consumer, U param) {
        if (obj != null) {
            consumer.accept(obj, param);
        }
    }

    public static <T> void ifPresentOrElse(T obj, Consumer<T> consumer, Executable runnable) {
        if (obj == null) {
            runnable.execute();
        } else {
            consumer.accept(obj);
        }
    }

    public static <T> void ifPresentOrThrow(T obj, Consumer<T> consumer) {
        if (obj == null) {
            throwRuntime();
        } else {
            consumer.accept(obj);
        }
    }

    public static <T> void ifPresentOrThrow(T obj, Consumer<T> consumer, String message) {
        if (obj == null) {
            throwRuntime(message);
        } else {
            consumer.accept(obj);
        }
    }

    /*
     * get or else
     */

    public static <T> T getOrElse(T value, T defaultVal) {
        return value == null ? defaultVal : value;
    }

    public static <T> T orElseGet(T value, Supplier<T> defaultSupplier) {
        return value == null ? defaultSupplier.get() : value;
    }
}

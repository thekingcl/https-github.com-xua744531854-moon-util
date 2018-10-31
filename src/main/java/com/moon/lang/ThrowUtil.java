package com.moon.lang;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class ThrowUtil {

    private ThrowUtil() {
        noInstanceError();
    }

    public static <T> T throwRuntime() {
        throw new IllegalArgumentException();
    }

    public static <T> T throwRuntime(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException(msg);
        }
    }

    public static <T> T throwRuntime(Object reason) {
        if (reason instanceof RuntimeException) {
            throw (RuntimeException) reason;
        } else if (reason instanceof Throwable) {
            throw new IllegalArgumentException((Throwable) reason);
        } else {
            throw new IllegalArgumentException(String.valueOf(reason));
        }
    }

    public static <T> T wrapAndThrow(Throwable e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        } else if (e instanceof Throwable) {
            throw new IllegalArgumentException(e);
        }
        /*
         * 实际上这一步是不会执行的
         */
        return null;
    }

    public static <T> T wrapAndThrow(Throwable e, String msg) {
        throw new IllegalArgumentException(msg, e);
    }

    public static <EX extends Throwable> EX getCause(Throwable t, Class<EX> type) {
        Throwable able = t;
        while (!type.isInstance(able)) {
            able = able.getCause();
            if (able == null) {
                return null;
            }
        }
        return (EX) able;
    }

    /**
     * 用于私有构造方法等，抛出一个错误表示该类不能有实例存在
     */
    public static void noInstanceError() {
        noInstanceError("No " + StackTraceUtil.getPrevCallerTypeName() + " instances for you!");
    }

    /**
     * 用于私有构造方法等，抛出一个错误表示该类不能有实例存在
     */
    public static void noInstanceError(String message) {
        throw new AssertionError(message);
    }
}

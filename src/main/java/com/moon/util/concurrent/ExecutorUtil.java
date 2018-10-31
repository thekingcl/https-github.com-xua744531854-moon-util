package com.moon.util.concurrent;

import com.moon.util.support.ThreadPoolSupport;

import java.util.concurrent.*;

import static com.moon.lang.IntUtil.max;
import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.CPUUtil.coreCount;

/**
 * @author benshaoye
 */
public final class ExecutorUtil {

    private final static long DEFAULT_TIMEOUT_OF_SS = 10 * 1000;

    private ExecutorUtil() {
        noInstanceError();
    }

    /*
     * inner runner
     */

    public final static ThreadPoolExecutor defaultExecutor() {
        return ThreadPoolSupport.singleton();
    }

    public final static void setExecutor(ThreadPoolExecutor runner) {
        ThreadPoolSupport.set(runner);
    }


    /*
     * executes
     */

    public final static void loopRun(Runnable runner, long timeInterval, boolean ignoreAbnormal) {
        execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    run0();
                }
            }

            private void run0() {
                try {
                    runner.run();
                    synchronized (this) {
                        this.wait(Math.max(timeInterval, 1));
                    }
                } catch (Throwable e) {
                    if (!ignoreAbnormal) {
                        if (e instanceof InterruptedException) {
                            Thread.currentThread().isInterrupted();
                        }
                        throw new IllegalStateException(e);
                    }
                }
            }
        });
    }

    public final static void loopRun(Runnable runner, boolean ignoreAbnormal) {
        execute(() -> {
            try {
                runner.run();
            } catch (Exception e) {
                if (!ignoreAbnormal) {
                    throw new IllegalStateException(e);
                }
            }
            loopRun(runner, ignoreAbnormal);
        });
    }

    public final static void execute(Runnable runnable) {
        defaultExecutor().execute(runnable);
    }

    public final static Future<?> submit(Runnable runnable) {
        return defaultExecutor().submit(runnable);
    }

    public final static <T> Future<T> submit(Callable<T> callable) {
        return defaultExecutor().submit(callable);
    }

    /*
     * 最大线程数为处理器可用核心数
     */

    public static final ThreadPoolExecutor auto() {
        return auto(DEFAULT_TIMEOUT_OF_SS);
    }

    public static final ThreadPoolExecutor auto(long timeout) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());
    }

    public static final ThreadPoolExecutor auto(RejectedExecutionHandler rejected) {
        return auto(DEFAULT_TIMEOUT_OF_SS, rejected);
    }

    public static final ThreadPoolExecutor auto(long timeout, RejectedExecutionHandler rejected) {
        return new ThreadPoolExecutor(1, maxCount(),
            DEFAULT_TIMEOUT_OF_SS, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), rejected);
    }

    public static final ThreadPoolExecutor auto(ThreadFactory factory) {
        return auto(DEFAULT_TIMEOUT_OF_SS, factory);
    }

    public static final ThreadPoolExecutor auto(long timeout, ThreadFactory factory) {
        return new ThreadPoolExecutor(1, maxCount(),
            DEFAULT_TIMEOUT_OF_SS, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), factory);
    }

    public static final ThreadPoolExecutor auto(
        long timeout, ThreadFactory factory, RejectedExecutionHandler rejected) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), factory, rejected);
    }

    /*
     * threshold，指定最大任务数，最大线程数为 CPU 线程数 2 倍
     */

    public static final ThreadPoolExecutor threshold(int thresholdTasks) {
        return threshold(thresholdTasks, DEFAULT_TIMEOUT_OF_SS);
    }

    public static final ThreadPoolExecutor threshold(int thresholdTasks, long timeout) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(thresholdTasks));
    }

    public static final ThreadPoolExecutor threshold(int thresholdTasks, ThreadFactory factory) {
        return threshold(thresholdTasks, DEFAULT_TIMEOUT_OF_SS, factory);
    }

    public static final ThreadPoolExecutor threshold(
        int thresholdTasks, long timeout, ThreadFactory factory) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(thresholdTasks), factory);
    }

    public static final ThreadPoolExecutor threshold(
        int thresholdTasks, RejectedExecutionHandler rejected) {
        return threshold(thresholdTasks, DEFAULT_TIMEOUT_OF_SS, rejected);
    }

    public static final ThreadPoolExecutor threshold(
        int thresholdTasks, long timeout, RejectedExecutionHandler rejected) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(thresholdTasks), rejected);
    }

    public static final ThreadPoolExecutor threshold(
        int thresholdTasks, ThreadFactory factory, RejectedExecutionHandler rejected) {
        return threshold(thresholdTasks, DEFAULT_TIMEOUT_OF_SS, factory, rejected);
    }

    public static final ThreadPoolExecutor threshold(
        int thresholdTasks, long timeout, ThreadFactory factory, RejectedExecutionHandler rejected) {
        return new ThreadPoolExecutor(1, maxCount(),
            timeout, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(thresholdTasks), factory, rejected);
    }

    public static int maxCount() {
        return max(coreCount() * 2, 1);
    }
}

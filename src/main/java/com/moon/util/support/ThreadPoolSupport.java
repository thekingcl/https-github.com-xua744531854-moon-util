package com.moon.util.support;

import com.moon.lang.ref.FinalAccessor;
import com.moon.util.concurrent.ExecutorUtil;
import com.moon.util.concurrent.RejectedUtil;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class ThreadPoolSupport {
    private ThreadPoolSupport() {
        noInstanceError();
    }

    public final static ThreadPoolExecutor singleton() {
        return ThreadPool.SINGLETON;
    }

    public final static void set(ThreadPoolExecutor executor) {
        Objects.requireNonNull(executor);
        FinalAccessor<ThreadPoolExecutor> oldExecutor = FinalAccessor.of();
        synchronized (ThreadPool.SINGLETON) {
            oldExecutor.set(ThreadPool.SINGLETON);
            ThreadPool.SINGLETON = executor;
        }
        oldExecutor.ifPresent(old -> old.shutdown());
    }

    private static class ThreadPool {

        private static ThreadPoolExecutor SINGLETON;


        static {
            /**
             * 默认开启一个最少一个线程，最多 CPU 核心数 2 倍，任务队列最多 65536 个任务的线程池运行任务
             * 并且核心线程池允许超时
             */
            SINGLETON = ExecutorUtil.threshold(Character.MAX_VALUE, RejectedUtil.callerRun());
            SINGLETON.allowCoreThreadTimeOut(true);
        }
    }
}

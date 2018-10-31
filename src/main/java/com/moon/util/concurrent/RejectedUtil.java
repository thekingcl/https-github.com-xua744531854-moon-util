package com.moon.util.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class RejectedUtil {
    private RejectedUtil() {
        noInstanceError();
    }

    public static final RejectedExecutionHandler abort() {
        return new AbortPolicy();
    }

    public static final RejectedExecutionHandler callerRun() {
        return new CallerRunsPolicy();
    }

    public static final RejectedExecutionHandler discardOldest() {
        return new DiscardOldestPolicy();
    }

    public static final RejectedExecutionHandler discard() {
        return new DiscardPolicy();
    }
}

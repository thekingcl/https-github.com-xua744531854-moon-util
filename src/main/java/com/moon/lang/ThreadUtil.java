package com.moon.lang;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class ThreadUtil {
    private ThreadUtil() {
        noInstanceError();
    }

    public final static void start(Runnable runnable) {
        new Thread(runnable).start();
    }
}

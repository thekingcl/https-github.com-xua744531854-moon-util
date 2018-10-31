package com.moon.util;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class CPUUtil {

    private CPUUtil() {
        noInstanceError();
    }

    /**
     * 核心线程数
     *
     * @return
     */
    public static int coreCount() {
        return Runtime.getRuntime().availableProcessors();
    }
}

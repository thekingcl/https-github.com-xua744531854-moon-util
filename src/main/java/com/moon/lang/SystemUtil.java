package com.moon.lang;

import com.moon.util.ResourceUtil;

import java.io.InputStream;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class SystemUtil {
    private SystemUtil() {
        noInstanceError();
    }

    public final static boolean resourceExists(String path) {
        return ResourceUtil.resourceExists(path);
    }

    public final static InputStream getResourceAsInputStream(String path) {
        return ResourceUtil.getResourceAsInputStream(path);
    }

    public final static long now() {
        return System.currentTimeMillis();
    }
}

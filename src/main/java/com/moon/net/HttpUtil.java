package com.moon.net;

import com.moon.lang.ThrowUtil;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
public final class HttpUtil {

    /**
     * default http request timeout
     */
    final static int TIMEOUT = 60000;

    private HttpUtil() {
        ThrowUtil.noInstanceError();
    }
}

package com.moon.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.ThrowUtil.throwRuntime;

/**
 * @author benshaoye
 * @date 2018/9/17
 */
public final class InetUtil {

    private InetUtil() {
        noInstanceError();
    }

    public static String getLocalIP4() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return throwRuntime(e);
        }
    }
}

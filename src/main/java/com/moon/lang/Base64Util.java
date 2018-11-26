package com.moon.lang;

import java.util.Base64;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/18
 */
public final class Base64Util {

    private final static String DEFAULT_TO_BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private final static String DEFAULT_TO_BASE64URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

    private Base64Util() {
        noInstanceError();
    }

    public final static String toBase64(String normal) {
        return Base64.getEncoder().encodeToString(normal.getBytes());
    }

    public static String toString(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }

    public final static String toBase64() {
        return ThrowUtil.rejectAccessError();
    }
}

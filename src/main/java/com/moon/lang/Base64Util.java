package com.moon.lang;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.ThrowUtil.wrapAndThrow;

/**
 * @author benshaoye
 * @date 2018/9/18
 */
public final class Base64Util {
    private Base64Util() {
        noInstanceError();
    }

    public static String toBase64(String normal) {
        return new BASE64Encoder().encode(normal.getBytes());
    }

    public static String toString(String base64) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(base64));
        } catch (IOException e) {
            return wrapAndThrow(e);
        }
    }
}

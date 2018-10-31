package com.moon.lang;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class LangUtil {
    private LangUtil() {
        noInstanceError();
    }

    public static void when(boolean tested, Executable executable) {
        if (tested) {
            executable.execute();
        }
    }

    public static void whenOrElse(boolean tested, Executable testPass, Executable testFail) {
        if (tested) {
            testPass.execute();
        } else {
            testFail.execute();
        }
    }
}

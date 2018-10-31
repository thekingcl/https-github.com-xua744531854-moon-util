package com.moon.util.assertions;

import com.moon.util.Console;

import static com.moon.util.assertions.AssertUtil.format;

/**
 * @author benshaoye
 */
interface AssertionsFail {
    /**
     * 获取输出器
     *
     * @return
     */
    Console getConsole();

    /**
     * 设置是否允许输出
     *
     * @param allowConsole
     * @return
     */
    AssertionsFail setAllowConsole(boolean allowConsole);

    /**
     * 设置在测试失败时是否抛出异常
     *
     * @param allowThrow
     * @return
     */
    AssertionsFail setAllowThrow(boolean allowThrow);

    /**
     * 是否允许输出内容
     *
     * @return
     */
    boolean isAllowConsole();

    /**
     * 是否允许抛出异常
     *
     * @return
     */
    boolean isAllowThrow();

    /**
     * 运行测试
     *
     * @param template
     * @param assertResult
     * @param value
     * @return
     */
    default boolean success(String template, boolean assertResult, Object value) {
        if (assertResult) {
            if (isAllowConsole()) {
                getConsole().test(format(template, assertResult, value));
            }
        } else if (isAllowThrow()) {
            throw new AssertionError(format(template, assertResult, value));
        } else if (!getConsole().isClosed()) {
            getConsole().test(format(template, assertResult, value));
        }
        return assertResult;
    }

    /**
     * 运行测试
     *
     * @param template
     * @param assertResult
     * @param value1
     * @param value2
     * @return
     */
    default boolean success(String template, boolean assertResult, Object value1, Object value2) {
        if (assertResult) {
            if (isAllowConsole()) {
                getConsole().test(format(template, assertResult, value1, value2));
            }
        } else if (isAllowThrow()) {
            throw new AssertionError(format(template, assertResult, value1, value2));
        } else if (!getConsole().isClosed()) {
            getConsole().test(format(template, assertResult, value1, value2));
        }
        return assertResult;
    }
}

package com.moon.util.assertions;

/**
 * @author benshaoye
 */
public interface Assertions extends
    AssertionsString, AssertionsObject, AssertionsBoolean,
    AssertionsThrows, AssertionsMath, AssertionsType,
    AssertionsEmpty {

    /**
     * 默认：
     * 通过 —— 打印信息
     * 失败 —— 抛出异常，终止程序
     *
     * @return
     */
    static Assertions of() {
        return new GenericAssertions();
    }

    /**
     * 无任何信息输出，失败抛出异常并退出程序
     *
     * @return
     */
    static Assertions ofThrow() {
        Assertions assertions = of().setAllowConsole(false);
        assertions.getConsole().setAllowOutputStatus(false);
        return assertions;
    }

    /**
     * 只输出信息，不抛出异常
     *
     * @return
     */
    static Assertions ofPrintln() {
        return of().setAllowThrow(false);
    }

    /**
     * 只输出错误信息，不抛出异常，不打印成功信息
     *
     * @return
     */
    static Assertions ofError() {
        return ofThrow().setAllowThrow(false);
    }

    /**
     * 不输出任何信息，不抛出任何异常，只返回是否符合条件
     *
     * @return
     */
    static Assertions assertion() {
        Assertions assertions = ofError();
        assertions.getConsole().setAllowOutputStatus(false);
        return assertions;
    }

    /**
     * 设置是否允许输出
     *
     * @param allowConsole
     * @return
     */
    @Override
    Assertions setAllowConsole(boolean allowConsole);

    /**
     * 设置在测试失败时是否抛出异常
     *
     * @param allowThrow
     * @return
     */
    @Override
    Assertions setAllowThrow(boolean allowThrow);
}

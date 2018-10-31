package com.moon.util.assertions;

import com.moon.util.Console;
import com.moon.util.console.ConsoleControl;

/**
 * @author benshaoye
 */
@ConsoleControl(lowestLevel = Console.Level.ASSERT, isDefault = true)
public class GenericAssertions implements Assertions {

    private final Console console;

    private boolean allowConsole = true;
    private boolean allowThrows = true;

    public GenericAssertions() {
        this(new AssertionsConsolePrinter());
    }

    public GenericAssertions(Console console) {
        this.console = console;
    }

    /**
     * 获取输出器
     *
     * @return
     */
    @Override
    public Console getConsole() {
        return console;
    }

    /**
     * 设置是否允许输出
     *
     * @param allowConsole
     * @return
     */
    @Override
    public Assertions setAllowConsole(boolean allowConsole) {
        this.allowConsole = allowConsole;
        return this;
    }

    /**
     * 设置在测试失败时是否抛出异常
     *
     * @param allowThrow
     * @return
     */
    @Override
    public Assertions setAllowThrow(boolean allowThrow) {
        this.allowThrows = allowThrow;
        return this;
    }

    /**
     * 是否允许输出内容
     *
     * @return
     */
    @Override
    public boolean isAllowConsole() {
        return allowConsole;
    }

    /**
     * 是否允许抛出异常
     *
     * @return
     */
    @Override
    public boolean isAllowThrow() {
        return allowThrows;
    }
}

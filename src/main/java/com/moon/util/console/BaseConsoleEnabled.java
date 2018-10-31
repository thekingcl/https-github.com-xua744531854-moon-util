package com.moon.util.console;

import com.moon.util.Console;
import com.moon.util.Console.Level;

import static com.moon.util.console.ConsoleGlobal.CONSOLE;

/**
 * @author benshaoye
 */
abstract class BaseConsoleEnabled implements ConsoleEnabled {

    private boolean allowSystemOut = true;
    private Level lowestLevel = Console.LOWEST;

    /**
     * 设置最低输出级别
     *
     * @param lowestLevel
     */
    @Override
    public synchronized boolean setLowestLevel(Level lowestLevel) {
        this.lowestLevel = lowestLevel;
        return true;
    }

    @Override
    public Level getLowestLevel() {
        return lowestLevel;
    }

    @Override
    public synchronized BaseConsoleEnabled setAllowSystemOut(boolean allowSystemOut) {
        this.allowSystemOut = allowSystemOut;
        return this;
    }

    @Override
    public boolean isAllowSystemOut() {
        return CONSOLE.isAllowSystemOut() && allowSystemOut;
    }

    /**
     * 是否允许指定级别控制
     *
     * @param level
     * @return
     */
    @Override
    public boolean isEnabled(Level level) {
        return CONSOLE.isEnabled(level) && level.ordinal() <= this.lowestLevel.ordinal();
    }
}

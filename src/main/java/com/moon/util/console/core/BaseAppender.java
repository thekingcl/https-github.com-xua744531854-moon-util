package com.moon.util.console.core;

import com.moon.util.Console;
import com.moon.util.console.ConsoleGlobal;
import com.moon.util.console.ConsoleSettings;

import java.io.File;

/**
 * @author benshaoye
 */
public abstract class BaseAppender {

    private final ConsoleSettings settings;
    private long nextTime;

    protected BaseAppender(ConsoleSettings settings) {
        this.settings = settings;
    }

    public ConsoleSettings getSettings() {
        return settings;
    }

    public File getBasePath() {
        return ConsoleGlobal.CONSOLE.getBasePath();
    }

    public File getDirectory(File base, Console.Level level) {

        return base;
    }
}

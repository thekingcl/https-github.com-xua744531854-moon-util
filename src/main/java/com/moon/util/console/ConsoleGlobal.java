package com.moon.util.console;

import com.moon.enums.PropsEnum;
import com.moon.util.Console;
import com.moon.util.Console.Level;

import java.io.File;

/**
 * @author benshaoye
 */
public enum ConsoleGlobal implements ConsoleEnabled {

    CONSOLE;

    private ConsoleSettings settings = ConsoleSettings.DEFAULT;
    private boolean allowSystemOut = true;
    private Level GLOBAL_LEVEL = Console.LOWEST;
    private File basePath;

    ConsoleGlobal() {
        // 留着初始化 base path
        setBasePath(PropsEnum.moonBasePath.value());
    }

    @Override
    public synchronized ConsoleGlobal setAllowSystemOut(boolean allowSystemOut) {
        this.allowSystemOut = allowSystemOut;
        return this;
    }

    @Override
    public boolean isAllowSystemOut() {
        return allowSystemOut;
    }

    /**
     * 设置最低输出级别
     *
     * @param lowestLevel
     */
    @Override
    public synchronized boolean setLowestLevel(Level lowestLevel) {
        GLOBAL_LEVEL = lowestLevel;
        return true;
    }

    /**
     * 返回最多输出级别
     *
     * @return
     */
    @Override
    public Level getLowestLevel() {
        return GLOBAL_LEVEL;
    }

    @Override
    public boolean isEnabled(Level level) {
        return level.ordinal() <= GLOBAL_LEVEL.ordinal();
    }

    public File getBasePath() {
        return basePath;
    }

    private void setBasePath(String basePath) {
        if (basePath != null) {
            setBasePath(new File(basePath));
        }
    }

    private void setBasePath(File basePath) {
        if (basePath != null) {
            if (basePath.exists() || basePath.mkdirs()) {
                File console = new File(basePath, name());
                basePath = console.mkdirs() ? console : basePath;
                synchronized (this) {
                    if (this.basePath == null) {
                        this.basePath = basePath;
                    }
                }
            } else {
                System.err.println("[ERROR] Not a valid path: " + basePath);
            }
        }
    }

    public ConsoleSettings getSettings() {
        return settings == null ? ConsoleSettings.DEFAULT : settings;
    }

    public synchronized void setSettings(ConsoleSettings settings) {
        this.settings = settings;
    }
}

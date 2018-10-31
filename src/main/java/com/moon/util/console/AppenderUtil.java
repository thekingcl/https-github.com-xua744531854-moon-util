package com.moon.util.console;

import com.moon.util.Appender;
import com.moon.util.console.core.HtmlAppender;
import com.moon.util.console.core.TextAppender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.console.ConsoleSettings.DEFAULT;

/**
 * @author benshaoye
 */
final class AppenderUtil {
    private AppenderUtil() {
        noInstanceError();
    }

    private final static Map<ConsoleSettings, Appender> APPENDER_MAP;

    static {
        (APPENDER_MAP = new ConcurrentHashMap<>()).put(DEFAULT, Appender.SYSTEM);
    }

    public final static Appender buildBySettings(ConsoleSettings consoleSettings) {
        return APPENDER_MAP.computeIfAbsent(consoleSettings, settings ->
            settings.isHtml() ? new HtmlAppender(settings) : new TextAppender(settings));
    }
}

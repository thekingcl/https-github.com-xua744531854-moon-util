package com.moon.util.console.core;

import com.moon.util.Appender;
import com.moon.util.Console;
import com.moon.util.console.ConsoleSettings;

import java.io.File;
import java.io.PrintStream;

/**
 * @author benshaoye
 */
public class TextAppender
    extends BaseAppender
    implements Appender {

    public TextAppender(ConsoleSettings settings) {
        super(settings);
    }

    /**
     * 获取输出对象
     *
     * @return
     */
    @Override
    public PrintStream apply(Console.Level level) {
        File base = getBasePath();
        if (base == null) {
            return System.out;
        } else {
            return System.out;
        }
    }
}

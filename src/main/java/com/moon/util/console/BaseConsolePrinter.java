package com.moon.util.console;

import com.moon.lang.ClassUtil;
import com.moon.util.Appender;
import com.moon.util.Console;
import com.moon.util.concurrent.ExecutorUtil;
import com.sun.istack.internal.NotNull;

import java.io.PrintStream;

import static com.moon.enums.PropsEnum.line_separator;
import static com.moon.util.Appender.SYSTEM;
import static com.moon.util.OptionalUtil.ifPresent;
import static com.moon.util.console.ConsoleSettingsUtil.parseByStackTraces;
import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
abstract class BaseConsolePrinter
    extends BaseConsoleEnabled
    implements Console {
    /**
     * 换行符
     */
    protected static final String LINE_SEPARATOR = line_separator.value();

    @NotNull
    protected final Class topClass;

    @NotNull
    protected final ConsoleSettings settings;

    protected boolean closeOutput = false;
    /**
     * 输出，必须存在
     */
    @NotNull
    private Appender appender;

    public BaseConsolePrinter() {
        this(IPrintable.class);
    }

    public BaseConsolePrinter(Class topClass) {
        this.topClass = requireNonNull(topClass);
        settings = parseByStackTraces(new Throwable().getStackTrace(), topClass);
        this.setAppender(AppenderUtil.buildBySettings(settings))
            .setLowestLevel(settings.getLowestLevel());
    }

    @Override
    public final synchronized BaseConsolePrinter setAppender(Appender appender) {
        this.appender = requireNonNull(appender);
        return this;
    }

    @Override
    public boolean setLowestLevel(Level lowestLevel) {
        if (settings != null &&
            settings.getLowestLevel().ordinal()
                > lowestLevel.ordinal()) {
            return false;
        }
        return super.setLowestLevel(lowestLevel);
    }

    @Override
    public final synchronized void setAllowOutputStatus(boolean allowOutput) {
        this.closeOutput = !allowOutput;
    }

    @Override
    public boolean isClosed() {
        return closeOutput;
    }

    protected PrintStream getLevelStream(Level level) {
        return isClosed() ? null
            : isEnabled(level)
            ? (appender == SYSTEM
            ? (isAllowSystemOut()
            ? SYSTEM.apply(level)
            : null)
            : requireNonNull(appender.apply(level)))
            : null;
    }

    final static String AT = "\tat ";
    final static String POINTER_JAVA = ".java:";
    final static char POINTER = '.';
    final static char LEFT_BRACKETS = '(';
    final static char RIGHT_BRACKETS = ')';

    @Override
    public void printStackTrace(Level level) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            StringBuilder builder = null;
            try {
                StackTraceElement[] traces = currentThread().getStackTrace();
                int i = 1, counter = 1, len = traces.length;
                StackTraceElement elem;
                boolean find = false;
                String className;
                Class clazz;

                builder = new StringBuilder(len * 100);
                for (; i < len; i++) {
                    elem = traces[i];
                    className = elem.getClassName();
                    clazz = ClassUtil.forName(className);
                    if (topClass.isAssignableFrom(clazz)) {
                        find = true;
                    } else if (find) {
                        builder.append(counter++).append(AT)
                            .append(className).append(POINTER)
                            .append(elem.getMethodName()).append(LEFT_BRACKETS)
                            .append(clazz.getSimpleName()).append(POINTER_JAVA)
                            .append(elem.getLineNumber()).append(RIGHT_BRACKETS)
                            .append(LINE_SEPARATOR);
                    }
                }
                executePrintOutput(stream, builder);
            } catch (Throwable t) {
                ifPresent(builder, (b, ps) -> executePrintOutput(ps, b), stream);
            }
        }
    }

    @Override
    public void println(Throwable t) {
        println(Level.DEBUG, t);
    }

    public void println(Level level, Throwable t) {
        ifPresent(getLevelStream(level), this::executePrintThrowable, t);
    }

    /*
     * 异步执行输出
     */

    protected void executePrintln(PrintStream stream) {
        if (stream != null) {
            if (settings.isAsync()) {
                ExecutorUtil.execute(() -> stream.println());
            } else {
                stream.println();
            }
        }
    }

    protected void executePrintOutput(PrintStream stream, Object o) {
        if (settings.isAsync()) {
            ExecutorUtil.execute(() -> stream.print(o));
        } else {
            stream.print(o);
        }
    }

    protected void executePrintlnOutput(PrintStream stream, Object o) {
        if (settings.isAsync()) {
            ExecutorUtil.execute(() -> stream.println(o));
        } else {
            stream.println(o);
        }
    }

    protected void executePrintThrowable(PrintStream stream, Throwable t) {
        if (settings.isAsync()) {
            ExecutorUtil.execute(() -> t.printStackTrace(stream));
        } else {
            t.printStackTrace(stream);
        }
    }
}

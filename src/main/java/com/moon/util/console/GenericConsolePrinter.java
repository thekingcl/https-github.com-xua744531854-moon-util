package com.moon.util.console;

import com.moon.lang.JoinerUtil;
import com.moon.lang.StringUtil;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static com.moon.enums.ArraysEnum.getOrObjects;

/**
 * @author benshaoye
 */
public class GenericConsolePrinter extends BaseConsolePrinter {


    public GenericConsolePrinter() {
    }

    public GenericConsolePrinter(Class topClass) {
        super(topClass);
    }

    /**
     * 换行
     *
     * @param level level
     */
    @Override
    public void println(Level level) {
        executePrintln(getLevelStream(level));
    }

    /**
     * 输出指定级别信息
     *
     * @param level level
     * @param val   值
     */
    @Override
    public void println(Level level, Object val) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            if (val instanceof Collection) {
                this.executePrintlnOutput(stream, JoinerUtil.join((Collection) val));
            } else if (val == null) {
                this.executePrintlnOutput(stream, null);
            } else {
                Class clazz = val.getClass();
                if (clazz.isArray()) {
                    this.executePrintlnOutput(stream, getOrObjects(clazz).stringify(val));
                } else {
                    executePrintlnOutput(stream, val);
                }
            }
        }
    }

    /**
     * 输出指定级别信息
     *
     * @param level    level
     * @param template 模板，‘{}’占位符
     * @param value    value
     */
    @Override
    public void println(Level level, String template, Object value) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            executePrintlnOutput(stream, StringUtil.format(template, value));
        }
    }

    /**
     * 输出指定级别信息
     *
     * @param level    level
     * @param template 模板，‘{}’占位符
     * @param value1   the first value
     * @param value2   the second value
     */
    @Override
    public void println(Level level, String template, Object value1, Object value2) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            executePrintlnOutput(stream, StringUtil.format(template, value1, value2));
        }
    }

    /**
     * 输出指定级别信息
     *
     * @param level    level
     * @param template 模板，‘{}’占位符
     * @param value1   the first value
     * @param value2   the second value
     * @param value3   the third value
     */
    @Override
    public void println(Level level, String template, Object value1, Object value2, Object value3) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            executePrintlnOutput(stream,
                StringUtil.format(template, value1, value2, value3));
        }
    }

    /**
     * 输出指定级别信息
     *
     * @param level    level
     * @param template 模板，‘{}’占位符
     * @param values   values
     */
    @Override
    public void println(Level level, String template, Object... values) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            executePrintlnOutput(stream, StringUtil.format(template, values));
        }
    }

    /**
     * 输出指定级别信息
     *
     * @param level  level
     * @param values values
     */
    @Override
    public void println(Level level, Object... values) {
        PrintStream stream = getLevelStream(level);
        if (stream != null) {
            executePrintlnOutput(stream, Arrays.deepToString(values));
        }
    }

    private StringBuilder timerBuilder;
    private long previousTiming;

    /**
     * 开始计时
     */
    @Override
    public void time() {
        previousTiming = timing();
        throw new UnsupportedOperationException();
    }

    /**
     * 开始计时
     *
     * @param template
     */
    @Override
    public void time(String template) {
        previousTiming = timing();
        throw new UnsupportedOperationException();
    }

    /**
     * 计次计时
     */
    @Override
    public void timeNext() {
        previousTiming = timing();
        throw new UnsupportedOperationException();
    }

    /**
     * 计次计时
     *
     * @param template
     */
    @Override
    public void timeNext(String template) {
        previousTiming = timing();
        throw new UnsupportedOperationException();
    }

    /**
     * 打印当前所经过的时间
     */
    @Override
    public void timeEnd() {
        previousTiming = timing();
        throw new UnsupportedOperationException();
    }

    private long timing() {
        return System.nanoTime();
    }
}

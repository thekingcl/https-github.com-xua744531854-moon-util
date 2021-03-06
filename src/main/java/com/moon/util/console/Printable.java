package com.moon.util.console;

/**
 * @author benshaoye
 */
public interface Printable extends IAssertPrintable,
    IErrorPrintable, IWarnPrintable, IInfoPrintable,
    IPrintlnPrintable, IDebugPrintable, ConsoleEnabled {
}

package com.moon.util;

import com.moon.util.assertions.Assertions;
import com.moon.util.console.ConsoleControl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author benshaoye
 */
@ConsoleControl(lowestLevel = Console.Level.WARN)
class ConsoleTestTest {

    static final Assertions ASSERTIONS = Assertions.of();

    @Test
    void testOf() {
//        ASSERTIONS.assertEquals(1, 1);
    }

    @Test
    void testPrintStackTrace() {
        Console.out.printStackTrace();

    }

    @Test
    void testName() {
        Console.out.println("aaaaaaaaaaaaaaaa");
        Console.out.println("bbbbbbbbbbbbbbbbbbb");
        Console.out.println("ccccccccccccccccccccccc");

        Console.out.println(new File("/d:/invoice/").exists());
        Console.out.println(System.getProperty("consoleBasePath"));

        File file = new File("/d:/invoice/name/temp");
        Console.out.println(file.exists());
        File parent = file;
        Console.out.println((parent = parent.getParentFile()).exists());
        Console.out.println((parent = parent.getParentFile()).exists());
        Console.out.println((parent = parent.getParentFile()).exists());
        Console.out.println(parent);
        Console.out.println(parent.getParentFile());
        Console.out.println(file.mkdirs());
        Console.out.println(getClass().getSimpleName());
    }

}
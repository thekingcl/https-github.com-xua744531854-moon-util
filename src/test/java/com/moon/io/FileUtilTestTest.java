package com.moon.io;

import com.moon.util.Console;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author benshaoye
 */
class FileUtilTestTest {

    static final Assertions assertions = Assertions.of();

    String path;
    File file, dir;
    Object data;

    void outFreeSpace(String local) {
        try {
            file = new File(local);
            Console.out.println(file.getFreeSpace() + "b");
            Console.out.println((file.getFreeSpace() >> 10) + "kb");
            Console.out.println((file.getFreeSpace() >> 10 >> 10) + "mb");
            Console.out.println((file.getFreeSpace() >> 10 >> 10 >> 10) + "gb");
            assertions.assertTrue(FileUtil.exists(local));
            Console.out.println(FileUtil.length(local));
        } catch (Throwable t) {

        }
    }

    @Test
    void testExists() {
        outFreeSpace("a:");
        outFreeSpace("a:DriverA");
    }
}
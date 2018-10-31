package com.moon.util.assertions;

import com.moon.util.Console;
import com.moon.util.console.ConsoleControl;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
@ConsoleControl(lowestLevel = Console.Level.DEBUG)
class AssertionsTestTest {

    static final Assertions ASSERTIONS = Assertions.ofPrintln();

    @Test
    void testOf() {
        ASSERTIONS.assertNotInstanceOf(ASSERTIONS, Assertions.class);
        ASSERTIONS.getConsole().error("=====================");
    }

    @Test
    void testOfThrow() {
    }

    @Test
    void testOfPrintln() {
    }

    @Test
    void testOfError() {
    }

    @Test
    void testAssertion() {
    }
}
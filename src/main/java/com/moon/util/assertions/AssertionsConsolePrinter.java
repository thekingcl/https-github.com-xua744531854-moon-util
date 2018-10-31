package com.moon.util.assertions;

import com.moon.util.console.GenericConsolePrinter;

/**
 * @author benshaoye
 */
class AssertionsConsolePrinter
    extends GenericConsolePrinter {

    public AssertionsConsolePrinter() {
        super(AssertionsFail.class);
    }
}

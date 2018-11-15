package com.moon.util.compute.core;

import java.util.function.IntPredicate;

import static com.moon.util.compute.core.Constants.*;

/**
 * @author benshaoye
 */
enum IntTesters implements IntPredicate {
    FALSE {
        @Override
        public boolean test(int value) {
            return false;
        }
    }
}

package com.moon.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class ExecutorUtilTestTest {

    @Test
    void testRunner() {

    }

    @Test
    void testLoopRun() {
        AtomicInteger atomic = new AtomicInteger();
        ExecutorUtil.loopRun(() -> System.out.println(" - " + atomic.incrementAndGet()), 10, true);
        synchronized (this) {
            try {
                this.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
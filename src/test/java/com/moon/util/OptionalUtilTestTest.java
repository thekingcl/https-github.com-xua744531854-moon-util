package com.moon.util;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
class OptionalUtilTestTest {

    @Test
    void testApplyOrNull() {
        int count = 1000000;
        StringBuilder sb = new StringBuilder(count + 1);
        sb.append(1);
        for (int i = 0; i < count; i++) {
            sb.append(0);
        }
        BigInteger number = new BigInteger(sb.toString());
        number = number.add(number);
        System.out.println(number);
    }

    @Test
    void testApplyOrElse() {

    }

    @Test
    void testApplyOrGet() {
    }

    @Test
    void testIfPresent() {
    }

    @Test
    void testIfPresentOrElse() {
    }
}
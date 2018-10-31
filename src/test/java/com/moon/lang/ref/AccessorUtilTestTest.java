package com.moon.lang.ref;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author benshaoye
 */
class AccessorUtilTestTest {

    @Test
    void testOfTrue() {
        AccessorUtil.ofTrue();
        AccessorUtil.ofFalse();
        AccessorUtil.ofBoolean(true);
        AccessorUtil.ofFinal();
        AccessorUtil.ofFinal("");
        AccessorUtil.ofWeak(() -> "");
        AccessorUtil.ofInt();
        AccessorUtil.ofInt(0);
        AccessorUtil.ofLong();
        AccessorUtil.ofLong(0L);
        AccessorUtil.ofDouble();
        AccessorUtil.ofDouble(0D);


        AccessorUtil.ofTrue().flip().ifTrue(() ->
            System.out.println("=================")).ifFalse(() ->
            System.out.println("+++++++++++++++++"));

        int result = AccessorUtil.ofInt().add(20).compute(value -> value + 50).ifGt(50, System.out::println).get();
        System.out.println(result);
        BigDecimal decimal = AccessorUtil.ofFinal(BigDecimal.ONE).computeIfPresent(value ->
            value.add(BigDecimal.TEN.multiply(BigDecimal.TEN))).ifPresent(value ->
            System.out.println(value)).get();
        System.out.println(decimal);
    }
}
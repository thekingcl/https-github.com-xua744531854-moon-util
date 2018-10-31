package com.moon.lang.ref;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 * @date 2018/9/15
 */
class BooleanAccessorTestTest {

    @Test
    void testOfFalse() {
        FinalAccessor<String> accessor = FinalAccessor.of();
        BooleanAccessor.ofFalse()
            .flip().ifTrue(() ->
            accessor.set("今天天气很棒"))
            .setTrue().ifTrue(() ->
            accessor.set("明天天气很棒"));

        System.out.println(accessor);
    }
}
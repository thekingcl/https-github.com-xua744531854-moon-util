package com.moon.util.compute.core;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
final class DataGetterCurly implements AsGetter {
    final BiConsumer[] consumers;
    final Supplier creator;

    DataGetterCurly(BiConsumer[] consumers, Supplier creator) {
        this.consumers = consumers;
        this.creator = creator;
    }

    @Override
    public Object run(Object data) {
        Object result = creator.get();
        BiConsumer[] consumers = this.consumers;
        int length = consumers.length;
        for (int i = 0; i < length; i++) {
            consumers[i].accept(result, data);
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(consumers);
    }
}

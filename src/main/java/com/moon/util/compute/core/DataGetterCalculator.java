package com.moon.util.compute.core;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author benshaoye
 */
class DataGetterCalculator implements AsGetter {
    final AsRunner[] handlers;

    private DataGetterCalculator(List<AsRunner> handlers) {
        this.handlers = handlers.toArray(new AsRunner[handlers.size()]);
    }

    final static AsRunner valueOf(List<AsRunner> handlers) {
        AsRunner handler;
        int size = handlers.size();
        if (size == 0) {
            return DataConst.NULL;
        }
        if (size == 1 && (handler = handlers.get(0)).isValuer()) {
            return handler;
        }
        DataGetterCalculator calculator = new DataGetterCalculator(handlers);
        for (AsRunner current : handlers) {
            if (current.isValuer() && !current.isConst()) {
                return calculator;
            }
        }
        return DataConst.get(calculator.run());
    }

    @Override
    public Object run(Object data) {
        return use1(data);
    }

    private Object use1(Object data) {
        Deque<AsRunner> result = new LinkedList();
        AsRunner[] handlers = this.handlers;
        final int length = handlers.length;
        AsRunner right, left;
        AsRunner operator;
        for (int i = 0; i < length; i++) {
            operator = handlers[i];
            if (operator.isValuer()) {
                result.offerFirst(operator);
            } else if (operator.isHandler()) {
                right = result.pollFirst();
                left = result.pollFirst();
                result.offerFirst(
                    DataConst.temp(
                        operator.handle(right, left, data)
                    )
                );
            } else {
                throw new IllegalArgumentException(
                    "type of: " + operator.getClass()
                );
            }
        }
        return result.pollFirst().run();
    }

    private Object use0(Object data) {
        AsRunner[] handlers = this.handlers;
        final int length = handlers.length;
        Deque result = new LinkedList();
        AsRunner operator;
        Object right, left;
        for (int i = 0; i < length; i++) {
            operator = handlers[i];
            if (operator.isValuer()) {
                result.offerFirst(operator.run(data));
            } else if (operator.isHandler()) {
                right = result.pollFirst();
                left = result.pollFirst();
                result.offerFirst(
                    operator.handle(right, left)
                );
            } else {
                throw new IllegalArgumentException(
                    "type of: " + operator.getClass()
                );
            }
        }
        return result.pollFirst();
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param o the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(handlers);
    }
}

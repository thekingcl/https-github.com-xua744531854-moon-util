package com.moon.util.compute.core;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author benshaoye
 */
class DataGetterCalculator implements AsGetter {
    final AsHandler[] handlers;

    private DataGetterCalculator(List<AsHandler> handlers) {
        this.handlers = handlers.toArray(new AsHandler[handlers.size()]);
    }

    final static AsHandler valueOf(List<AsHandler> handlers) {
        AsHandler handler;
        int size = handlers.size();
        if (size == 0) {
            return DataConst.NULL;
        }
        if (size == 1 && (handler = handlers.get(0)).isValuer()) {
            return handler;
        }
        DataGetterCalculator calculator = new DataGetterCalculator(handlers);
        for (AsHandler current : handlers) {
            if (current.isValuer() && !current.isConst()) {
                return calculator;
            }
        }
        return DataConst.get(calculator.use());
    }

    @Override
    public Object use(Object data) {
        return use1(data);
    }

    private Object use1(Object data) {
        Deque<AsHandler> result = new LinkedList();
        AsHandler[] handlers = this.handlers;
        final int length = handlers.length;
        AsHandler right, left;
        AsHandler operator;
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
        return result.pollFirst().use();
    }

    private Object use0(Object data) {
        AsHandler[] handlers = this.handlers;
        final int length = handlers.length;
        Deque result = new LinkedList();
        AsHandler operator;
        Object right, left;
        for (int i = 0; i < length; i++) {
            operator = handlers[i];
            if (operator.isValuer()) {
                result.offerFirst(operator.use(data));
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

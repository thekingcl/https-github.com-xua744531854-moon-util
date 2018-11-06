package com.moon.util.compute.core;

import com.moon.beans.BeanInfoUtil;
import com.moon.beans.FieldDescriptor;
import com.moon.enums.ArrayOperators;
import com.moon.enums.ArraysEnum;
import com.moon.lang.BooleanUtil;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 从输入数据中获取值
 *
 * @author benshaoye
 */
class DataGetterOrdinary implements AsGetter {

    final Object key;
    final String message;
    final int index;

    private AsGetter getter;

    DataGetterOrdinary(Object key) {
        BooleanUtil.requireFalse(key instanceof AsHandler);
        this.key = key;
        this.message = "Variable of name: " + String.valueOf(key);
        if (key instanceof Integer) {
            index = ((Number) key).intValue();
        } else {
            index = -1;
        }
    }

    @Override
    public boolean isGetterOrdinary() {
        return true;
    }

    public AsGetter getGetter(Object data) {
        AsGetter getter = this.getter;
        if (getter == null) {
            getter = resetGetter(data);
        }
        return getter;
    }

    @Override
    public Object use(Object data) {
        try {
            return getGetter(data).use(data);
        } catch (Exception e) {
            return resetGetter(data).use(data);
        }
    }

    public Object getKey() {
        return key;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }

    private AsGetter resetGetter(Object data) {
        Objects.requireNonNull(data, message);
        AsGetter getter;
        if (data instanceof Map) {
            getter = new MapGetter(key);
        } else if (data instanceof List) {
            BooleanUtil.requireTrue(index >= 0, message);
            getter = new ListGetter(index);
        } else if (data.getClass().isArray()) {
            BooleanUtil.requireTrue(index >= 0, message);
            getter = new ArrayGetter(index);
        } else {
            Objects.requireNonNull(data, message);
            BooleanUtil.requireTrue(key instanceof String, message);
            getter = new FieldGetter(key);
        }
        this.getter = getter;
        return getter;
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
        return getter == null
            ? resetGetter(o).test(o)
            : getter.test(o)
            || resetGetter(o).test(o);
    }

    /*
     * -------------------------------------------------------------
     * classes
     * -------------------------------------------------------------
     */

    private static class MapGetter implements AsGetter {
        final Object key;

        MapGetter(Object key) {
            this.key = key;
        }

        /**
         * 使用外部数据
         *
         * @param data
         * @return
         */
        @Override
        public Object use(Object data) {
            return MapUtil.getByObject(data, key);
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
            return o instanceof Map;
        }
    }

    private static class ListGetter implements AsGetter {
        final int index;

        ListGetter(int index) {
            this.index = index;
        }

        /**
         * 使用外部数据
         *
         * @param data
         * @return
         */
        @Override
        public Object use(Object data) {
            return ListUtil.getByObject(data, index);
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
            return o instanceof List;
        }
    }

    private static class ArrayGetter implements AsGetter {
        final int index;
        final String message;
        ArrayOperators getter;

        ArrayGetter(int index) {
            this.index = index;
            this.message = String.valueOf(index);
        }

        /**
         * 使用外部数据
         *
         * @param data
         * @return
         */
        @Override
        public Object use(Object data) {
            Objects.requireNonNull(data, message);
            if (getter == null || getter.test(data)) {
                getter = reset(data);
            }
            return getter.get(data,index);
        }

        private ArrayOperators reset(Object data) {
            return getter = ArraysEnum.getOrObjects(data.getClass());
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
            return getter == null
                ? reset(o).test(o)
                : getter.test(o)
                || reset(o).test(o);
        }
    }

    private static class FieldGetter implements AsGetter {
        final String field;
        FieldDescriptor getter;

        FieldGetter(Object field) {
            this.field = field.toString();
        }

        /**
         * 使用外部数据
         *
         * @param data
         * @return
         */
        @Override
        public Object use(Object data) {
            Objects.requireNonNull(data, field);
            if (getter == null) {
                getter = BeanInfoUtil.getFieldDescriptor(data.getClass(), field);
            }
            try {
                return getter.getValue(data, true);
            } catch (Exception e) {
                return reset(data).getValue(data, true);
            }
        }

        private FieldDescriptor reset(Object data) {
            getter = BeanInfoUtil.getFieldDescriptor(data.getClass(), field);
            return getter;
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
            return getter == null
                ? reset(o).getDeclaringClass().isInstance(o)
                : getter.getDeclaringClass().isInstance(o)
                || reset(o).getDeclaringClass().isInstance(o);
        }
    }
}

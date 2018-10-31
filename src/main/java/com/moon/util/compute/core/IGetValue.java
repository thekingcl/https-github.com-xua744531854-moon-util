package com.moon.util.compute.core;

import com.moon.lang.reflect.FieldUtil;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;

import java.util.List;
import java.util.Map;

/**
 * @author benshaoye
 */
enum IGetValue implements IGetter {
    MAP {
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

        /**
         * Applies this function to the given arguments.
         *
         * @param o  the first function argument
         * @param o2 the second function argument
         * @return the function result
         */
        @Override
        public Object apply(Object o, Object o2) {
            return MapUtil.getByObject(o, o2);
        }
    },
    LIST {
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

        /**
         * Applies this function to the given arguments.
         *
         * @param o  the first function argument
         * @param o2 the second function argument
         * @return the function result
         */
        @Override
        public Object apply(Object o, Object o2) {
            return ListUtil.getByObject(o, ((Number) o2).intValue());
        }
    },
    BEAN {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param o the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object o) {
            return !(o instanceof Map
                || (o instanceof List)
                || o.getClass().isArray());
        }

        /**
         * Applies this function to the given arguments.
         *
         * @param o  the first function argument
         * @param o2 the second function argument
         * @return the function result
         */
        @Override
        public Object apply(Object o, Object o2) {
            return FieldUtil.getValue(o2.toString(), o, true);
        }
    }
}

package com.moon.enums;

import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public enum PredicateEnum implements Predicate {
    TRUE {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param object the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object object) {
            return true;
        }
    },
    FALSE {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param object the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object object) {
            return false;
        }
    },
    NULL {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param object the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object object) {
            return object == null;
        }
    },
    NON_NULL {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param object the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object object) {
            return object != null;
        }
    }
}

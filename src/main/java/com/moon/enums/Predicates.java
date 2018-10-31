package com.moon.enums;

import java.util.function.Predicate;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
public enum Predicates implements Predicate {
    TRUE {
        @Override
        public boolean test(Object o) {
            return true;
        }
    },
    FALSE {
        @Override
        public boolean test(Object o) {
            return false;
        }
    },
    NULL {
        @Override
        public boolean test(Object o) {
            return o == null;
        }
    },
    NON_NULL {
        @Override
        public boolean test(Object o) {
            return o != null;
        }
    }
}

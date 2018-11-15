package com.moon.util.compute.core;

import java.util.Objects;

/**
 * @author benshaoye
 */
enum DataComputes implements AsCompute {
    /**
     * 这个符号仅用于提升优先级的标记，没有计算意义
     */
    YUAN_LEFT(ConstPriorities.MAX),

    BIT_LEFT(ConstPriorities.BIT_LEFT) {
        @Override
        public Object handle(Object o2, Object o1) {
            return ((Number) o1).intValue() << ((Number) o2).intValue();
        }
    },
    BIT_RIGHT(ConstPriorities.BIT_RIGHT) {
        @Override
        public Object handle(Object o2, Object o1) {
            return ((Number) o1).intValue() >> ((Number) o2).intValue();
        }
    },
    BIT_AND(ConstPriorities.BIT_AND) {
        @Override
        public Object handle(Object o2, Object o1) {
            return ((Number) o1).intValue() & ((Number) o2).intValue();
        }
    },
    BIT_OR(ConstPriorities.BIT_OR) {
        @Override
        public Object handle(Object o2, Object o1) {
            return ((Number) o1).intValue() | ((Number) o2).intValue();
        }
    },
    NOT_OR(ConstPriorities.NOT_OR) {
        @Override
        public Object handle(Object o2, Object o1) {
            return ((Number) o1).intValue() ^ ((Number) o2).intValue();
        }
    },

    PLUS(ConstPriorities.PLUS) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 instanceof Number && o2 instanceof Number) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    return ((Number) o1).intValue() + ((Number) o2).intValue();
                }
                return ((Number) o1).doubleValue() + ((Number) o2).doubleValue();
            }
            return String.valueOf(o1) + String.valueOf(o2);
        }
    },
    MINUS(ConstPriorities.MINUS) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 instanceof Double
                || o2 instanceof Double
                || o1 instanceof Float
                || o2 instanceof Float) {
                return ((Number) o1).doubleValue() - ((Number) o2).doubleValue();
            }
            return ((Number) o1).intValue() - ((Number) o2).intValue();
        }
    },
    MULTI(ConstPriorities.MULTI) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 instanceof Double
                || o2 instanceof Double
                || o1 instanceof Float
                || o2 instanceof Float) {
                return ((Number) o1).doubleValue() * ((Number) o2).doubleValue();
            }
            return ((Number) o1).intValue() * ((Number) o2).intValue();
        }
    },
    DIVIDE(ConstPriorities.DIVIDE) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 instanceof Double
                || o2 instanceof Double
                || o1 instanceof Float
                || o2 instanceof Float) {
                return ((Number) o1).doubleValue() / ((Number) o2).doubleValue();
            }
            return ((Number) o1).intValue() / ((Number) o2).intValue();
        }
    },
    MOD(ConstPriorities.MOD) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 instanceof Double
                || o2 instanceof Double
                || o1 instanceof Float
                || o2 instanceof Float) {
                return ((Number) o1).doubleValue() % ((Number) o2).doubleValue();
            }
            return ((Number) o1).intValue() % ((Number) o2).intValue();
        }
    },
    AND(ConstPriorities.AND) {
        @Override
        public Object handle(Object o2, Object o1) {
            throw new UnsupportedOperationException();
        }

        /**
         * 计算
         *
         * @param right
         * @param left
         * @param data
         * @return
         */
        @Override
        public Object handle(AsRunner right, AsRunner left, Object data) {
            return (Boolean) left.run(data) && (Boolean) right.run(data);
        }
    },
    OR(ConstPriorities.OR) {
        @Override
        public Object handle(Object o2, Object o1) {
            throw new UnsupportedOperationException();
        }

        /**
         * 计算
         *
         * @param right
         * @param left
         * @param data
         * @return
         */
        @Override
        public Object handle(AsRunner right, AsRunner left, Object data) {
            return (Boolean) left.run(data) || (Boolean) right.run(data);
        }
    },
    EQ(ConstPriorities.EQ) {
        @Override
        public Object handle(Object o2, Object o1) {
            return o1 == o2 || Boolean.valueOf(Objects.equals(o1, o2));
        }
    },
    GT(ConstPriorities.GT) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 == o2 || o1 == null) {
                return Boolean.FALSE;
            }
            if (o2 == null) {
                return Boolean.TRUE;
            }
            return ((Comparable) o1).compareTo(o2) > 0;
        }
    },
    LT(ConstPriorities.LT) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 == o2 || o2 == null) {
                return Boolean.FALSE;
            }
            if (o1 == null) {
                return Boolean.TRUE;
            }
            return ((Comparable) o1).compareTo(o2) < 0;
        }
    },
    GT_OR_EQ(ConstPriorities.GT_OR_EQ) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 == o2 || o2 == null) {
                return Boolean.TRUE;
            }
            if (o1 == null) {
                return Boolean.FALSE;
            }
            return ((Comparable) o1).compareTo(o2) >= 0;
        }
    },
    LT_OR_EQ(ConstPriorities.LT_OR_EQ) {
        @Override
        public Object handle(Object o2, Object o1) {
            if (o1 == o2 || o1 == null) {
                return Boolean.TRUE;
            }
            if (o2 == null) {
                return Boolean.FALSE;
            }
            return ((Comparable) o1).compareTo(o2) <= 0;
        }
    };

    private final int priority;

    DataComputes(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}

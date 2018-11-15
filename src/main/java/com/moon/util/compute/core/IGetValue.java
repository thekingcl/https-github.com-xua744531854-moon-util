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
        @Override
        public boolean test(Object o) {
            return o instanceof Map;
        }

        @Override
        public Object apply(Object o, Object o2) {
            return MapUtil.getByObject(o, o2);
        }
    },
    LIST {
        @Override
        public boolean test(Object o) {
            return o instanceof List;
        }

        @Override
        public Object apply(Object o, Object o2) {
            return ListUtil.getByObject(o, ((Number) o2).intValue());
        }
    },
    BEAN {
        @Override
        public boolean test(Object o) {
            return !(o instanceof Map || (o instanceof List) || o.getClass().isArray());
        }

        @Override
        public Object apply(Object o, Object o2) {
            return FieldUtil.getValue(o2.toString(), o, true);
        }
    }
}

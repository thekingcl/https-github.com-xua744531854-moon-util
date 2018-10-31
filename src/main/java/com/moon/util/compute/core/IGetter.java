package com.moon.util.compute.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author benshaoye
 */
interface IGetter extends BiFunction, Predicate {

    static IGetter reset(Object prevData, Object afterData) {
        IGetter getter = null;
        if (afterData instanceof Number) {
            if (prevData instanceof List) {
                getter = IGet.LIST;
            } else if (prevData instanceof Map) {
                getter = IGet.MAP;
            } else if (prevData.getClass().isArray()) {
                getter = new IGetArray();
            }
        } else if (afterData instanceof CharSequence) {
            if (prevData instanceof Map) {
                getter = IGet.MAP;
            } else {
                getter = IGet.BEAN;
            }
        } else if (prevData instanceof Map) {
            getter = IGet.MAP;
        } else if (prevData instanceof List) {
            getter = IGet.LIST;
        } else {
            getter = IGet.BEAN;
        }
        Objects.requireNonNull(getter);
        return getter;
    }
}

package com.moon.util;

import com.moon.lang.ThrowUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class SetUtil extends CollectUtil {
    private SetUtil() {
        ThrowUtil.noInstanceError();
    }

    /*
     * ---------------------------------------------------------------------------------
     * of hash set
     * ---------------------------------------------------------------------------------
     */

    public static <T> HashSet<T> ofHashSet() {
        return new HashSet<>();
    }

    public static <T> HashSet<T> ofHashSet(int initCapacity) {
        return new HashSet<>(initCapacity);
    }

    public static <T> HashSet<T> ofHashSet(T value) {
        HashSet<T> set = ofHashSet();
        set.add(value);
        return set;
    }

    public static <T> HashSet<T> ofHashSet(T value1, T value2) {
        HashSet<T> set = ofHashSet(value1);
        set.add(value2);
        return set;
    }

    public static <T> HashSet<T> ofHashSet(T value1, T value2, T value3) {
        HashSet<T> set = ofHashSet(value1, value2);
        set.add(value3);
        return set;
    }

    public static <T> HashSet<T> ofHashSet(T... values) {
        HashSet<T> set = ofHashSet(values.length);
        for (T value : values) {
            set.add(value);
        }
        return set;
    }

    public static <T> Set<T> concat(Set<T> set, Set<T>... sets) {
        return (Set) concat0(set, sets);
    }
}

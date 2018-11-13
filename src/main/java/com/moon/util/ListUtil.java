package com.moon.util;

import com.moon.enums.CollectionEnum;
import com.moon.lang.ThrowUtil;

import java.util.*;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class ListUtil extends CollectUtil {
    private ListUtil() {
        ThrowUtil.noInstanceError();
    }

    /*
     * ---------------------------------------------------------------------------------
     * of array valuesList
     * ---------------------------------------------------------------------------------
     */

    public static <T> ArrayList<T> ofArrayList() {
        return new ArrayList<>();
    }

    public static <T> ArrayList<T> ofArrayList(int initSize) {
        return new ArrayList<>(initSize);
    }

    public static <T> ArrayList<T> ofArrayList(T value) {
        return add(ofArrayList(), value);
    }

    public static <T> ArrayList<T> ofArrayList(T value1, T value2) {
        return add(ofArrayList(value1), value2);
    }

    public static <T> ArrayList<T> ofArrayList(T value1, T value2, T value3) {
        return add(ofArrayList(value1, value2), value3);
    }

    public static <T> ArrayList<T> ofArrayList(T... values) {
        return addAll(ofArrayList(values.length), values);
    }

    /*
     * ---------------------------------------------------------------------------------
     * of linked valuesList
     * ---------------------------------------------------------------------------------
     */

    public static <T> LinkedList<T> ofLinkedList() {
        return new LinkedList<>();
    }

    public static <T> LinkedList<T> ofLinkedList(T value) {
        return add(ofLinkedList(), value);
    }

    public static <T> LinkedList<T> ofLinkedList(T value1, T value2) {
        return add(ofLinkedList(value1), value2);
    }

    public static <T> LinkedList<T> ofLinkedList(T value1, T value2, T value3) {
        return add(ofLinkedList(value1, value2), value3);
    }

    public static <T> LinkedList<T> ofLinkedList(T... values) {
        return addAll(ofLinkedList(), values);
    }

    /*
     * ---------------------------------------------------------------------------------
     * keepers
     * ---------------------------------------------------------------------------------
     */

    /**
     * 如果集合是空集合（null 或 size() == 0）这返回 null
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> nullIfEmpty(List<T> list) {
        return list == null ? null : list.size() == 0 ? null : list;
    }

    /**
     * 如果 valuesList 是 null 则创建一个新的 ArrayList 返回
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null || list.size() == 0 ? ofArrayList() : list;
    }

    /**
     * 获取 valuesList 第一项，任何非法情况下都返回 null
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T nullableGetFirst(List<T> list) {
        return list == null ? null : list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 获取 valuesList 第一项，任何非法情况下都将抛出特定异常
     *
     * @param list
     * @param <T>
     * @return
     * @throws ArrayIndexOutOfBoundsException
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException
     */
    public static <T> T requireGetFirst(List<T> list) {
        return requireGet(list, 0);
    }

    /**
     * 获取 valuesList 最后一项，任何非法情况下都返回 null
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T nullableGetLast(List<T> list) {
        if (list != null) {
            int size = list.size();
            return size > 0 ? list.get(size - 1) : null;
        }
        return null;
    }

    /**
     * 获取 valuesList 最后一项，任何非法情况下都将抛出特定异常
     *
     * @param list
     * @param <T>
     * @return
     * @throws ArrayIndexOutOfBoundsException
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException
     */
    public static <T> T requireGetLast(List<T> list) {
        return requireGet(list, (list.size() - 1));
    }

    /**
     * 获取 valuesList 第 index 项，任何非法情况下都返回 null
     *
     * @param list
     * @param index
     * @param <T>
     * @return
     */
    public static <T> T nullableGet(List<T> list, int index) {
        if (list != null || index < 0) {
            int size = list.size();
            return index < size ? list.get(index) : null;
        }
        return null;
    }

    /**
     * 获取 valuesList 第 index 项，任何非法情况下都将抛出特定异常
     *
     * @param list
     * @param index
     * @param <T>
     * @return
     * @throws ArrayIndexOutOfBoundsException
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException
     */
    public static <T> T requireGet(List<T> list, int index) {
        T item = get(list, index);
        if (item == null) {
            throw new NullPointerException();
        }
        return item;
    }

    /*
     * ---------------------------------------------------------------------------------
     * gets
     * ---------------------------------------------------------------------------------
     */

    /**
     * 获取 valuesList 第 index 项，任何非法情况下都返回或第 index 项为 null 时将返回 defaultValue
     *
     * @param list
     * @param index
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getOrDefault(List<T> list, int index, T defaultValue) {
        T res = nullableGet(list, index);
        return res == null ? defaultValue : res;
    }

    /**
     * 获取 valuesList 最后一项，任何非法情况下都返回或最后一项为 null 时将返回 defaultValue
     *
     * @param list
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getLastOrDefault(List<T> list, T defaultValue) {
        T res = nullableGetLast(list);
        return res == null ? defaultValue : res;
    }

    /**
     * 获取 valuesList 第一项，任何非法情况下都返回或最后一项为 null 时将返回 defaultValue
     *
     * @param list
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getFirstOrDefault(List<T> list, T defaultValue) {
        T res = nullableGetFirst(list);
        return res == null ? defaultValue : res;
    }

    /**
     * 普通获取第 index 项
     *
     * @param list
     * @param index
     * @param <T>
     * @return
     */
    public static <T> T get(List<T> list, int index) {
        return list.get(index);
    }

    /**
     * 从一个声明类型是 Object 的 valuesList 集合获取第 index 项
     * 主要是为了屏蔽强制类型转换
     *
     * @param list
     * @param index
     * @param <T>
     * @return
     */
    public static <T> T getByObject(Object list, int index) {
        return (T) get(((List) list), index);
    }

    /*
     * ---------------------------------------------------------------------------------
     * operations
     * ---------------------------------------------------------------------------------
     */

    public static <T> List<T> concat(List<T> list, List<T>... lists) {
        return (List) concat0(list, lists);
    }

    /**
     * 去掉集合里的重复项
     *
     * @param list
     * @param <T>
     * @return 集合本身
     */
    public static <T> List<T> unique(List<T> list) {
        HashMap<T, Object> map = new HashMap<>(list.size());
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (map.containsKey(item)) {
                iterator.remove();
            } else {
                map.put(item, null);
            }
        }
        return list;
    }

    /**
     * 删除 List 集合中重复项，返回重复项集合
     *
     * @param list
     * @param <T>
     * @return 删除的重复项，默认用 ArrayList 包装
     */
    public static <T> List<T> removeRepeats(List<T> list) {
        List<T> repeated = null;
        if (list != null) {
            final int size = list.size();
            HashMap<T, Object> map = new HashMap<>(size);
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T item = iterator.next();
                if (map.containsKey(item)) {
                    if (repeated == null) {
                        CollectionEnum collect = CollectionEnum.getOrDefault(list, CollectionEnum.ArrayList);
                        try {
                            repeated = (List) collect.apply(size);
                        } catch (Throwable e) {
                            repeated = ofArrayList(size);
                        }
                    }
                    repeated.add(item);
                    iterator.remove();
                } else {
                    map.put(item, null);
                }
            }
        }
        return repeated;
    }
}

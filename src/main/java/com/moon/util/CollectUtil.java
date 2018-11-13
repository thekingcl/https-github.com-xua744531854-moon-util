package com.moon.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public class CollectUtil extends BaseCollectUtil {

    protected CollectUtil() {
        noInstanceError();
    }

    public static <E, C extends Collection<E>> int size(C collect) {
        return collect == null ? 0 : collect.size();
    }

    public static int sizeByObject(Object collect) {
        return collect == null ? 0 : ((Collection) collect).size();
    }

    public static <E, C extends Collection<E>> boolean isEmpty(C collect) {
        return collect == null ? true : collect.isEmpty();
    }

    public static <E, C extends Collection<E>> boolean isNotEmpty(C collect) {
        return !isEmpty(collect);
    }

    /*
     * ---------------------------------------------------------------------------------
     * adders
     * ---------------------------------------------------------------------------------
     */

    public static <E, C extends Collection<E>> C add(C collect, E element) {
        if (collect != null) {
            collect.add(element);
        }
        return collect;
    }

    public static <E, C extends Collection<E>> C add(C collect, E element1, E element2) {
        if (collect != null) {
            collect.add(element1);
            collect.add(element2);
        }
        return collect;
    }

    public static <E, C extends Collection<E>> C add(C collect, E element1, E element2, E element3) {
        if (collect != null) {
            collect.add(element1);
            collect.add(element2);
            collect.add(element3);
        }
        return collect;
    }

    public static <E, C extends Collection<E>> C addAll(C collect, E... elements) {
        if (collect != null) {
            for (E element : elements) {
                collect.add(element);
            }
        }
        return collect;
    }

    public static <E, C extends Collection<E>> C addAll(C collect, Collection<E> collection) {
        if (collect != null) {
            collect.addAll(collection);
        }
        return collect;
    }

    public static <E, C extends Collection<E>> C addAll(C collect, Iterable<E> iterable) {
        if (collect != null) {
            for (E elem : iterable) {
                collect.add(elem);
            }
        }
        return collect;
    }

    /*
     * ---------------------------------------------------------------------------------
     * converter
     * ---------------------------------------------------------------------------------
     */

    public static <T, O, C1 extends Collection<T>, C2 extends Collection<O>> C2 map(C1 src, Function<T, O> function) {
        return IteratorUtil.map(src, function);
    }

    public final static <T> Collection<T> concat(Collection<T> collection, Collection<T>... collections) {
        return concat0(collection, collections);
    }

    public final static <T> Set<T> toSet(T... items) {
        return SetUtil.ofHashSet(items);
    }

    public final static <T> List<T> toList(T... items) {
        return ListUtil.ofArrayList(items);
    }

    public final static <E, T> T[] toArray(Collection<E> collection, Class<T> componentType) {
        int index = 0;
        Object array = Array.newInstance(componentType, collection.size());
        for (Object item : collection) {
            Array.set(array, index++, item);
        }
        return (T[]) array;
    }

    /*
     * ---------------------------------------------------------------------------------
     * contains
     * ---------------------------------------------------------------------------------
     */

    public final static <T> boolean contains(Collection<T> collection, T item) {
        return collection != null && collection.contains(item);
    }

    public final static <T> boolean containsAny(Collection<T> collect, T item1, T item2) {
        return collect != null && (collect.contains(item1) || collect.contains(item2));
    }

    public final static <T> boolean containsAll(Collection<T> collect, T item1, T item2) {
        return collect != null && (collect.contains(item1) && collect.contains(item2));
    }

    public final static <T> boolean containsAny(Collection<T> collect, T item1, T item2, T item3) {
        return collect != null && (collect.contains(item1)
            || collect.contains(item2)
            || collect.contains(item3));
    }

    public final static <T> boolean containsAll(Collection<T> collect, T item1, T item2, T item3) {
        return collect != null && (collect.contains(item1)
            && collect.contains(item2)
            && collect.contains(item3));
    }

    public final static <T> boolean containsAny(Collection<T> collect1, Collection<T> collect2) {
        if (collect1 == collect2) {
            return true;
        }
        if (collect1.size() > collect2.size()) {
            for (T collect : collect1) {
                if (collect2.contains(collect)) {
                    return true;
                }
            }
        } else {
            for (T collect : collect2) {
                if (collect1.contains(collect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final static <T> boolean containsAll(Collection<T> collection1, Collection<T> collection2) {
        return collection1 == collection2 ? true : (collection1 != null && collection2.containsAll(collection2));
    }

    /*
     * ---------------------------------------------------------------------------------
     * matchers
     * ---------------------------------------------------------------------------------
     */

    public final static <T> boolean matchAny(Collection<T> collect, Predicate<T> matcher) {
        if (collect != null) {
            for (T item : collect) {
                if (matcher.test(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final static <T> boolean matchAll(Collection<T> collect, Predicate<T> matcher) {
        if (collect != null) {
            for (T item : collect) {
                if (!matcher.test(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

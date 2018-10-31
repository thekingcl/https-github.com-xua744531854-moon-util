package com.moon.enums;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * @author benshaoye
 * @date 2018/9/17
 */
public enum MapEnum implements Supplier<Map>,
    IntFunction<Map>, Function<Map, Map> {

    /**
     * HashMap（散列表） 是开发中使用最频繁的数据结构之一，它是基于对象的 hash 值实现的高速 key-value 映射存储结构
     * 一、HashMap 的存取原理：
     * 当我们调用方法{@link HashMap#put(Object, Object)}时，以 key 作为键，value 作为值，如果 key 已经对应有值，
     * 那么新值（value）将替换掉旧的值（oldValue）并返回旧值，否则将添加 key 对应的映射值 value 并返回 null，
     * 由此可认为 HashMap 的 put 方法总是返回 key 对应的旧值。
     * HahMap 内部使用一个数组（Map.Entry[]）来维护这个数据集合，Entry 本身又是一个链表，
     * 故 HashMap 内部维护数据的结构类似一个坐标系统；
     * <p>
     * 当向 HashMap put 值的时候，其过程主要是在{@link HashMap#putVal(int, Object, Object, boolean, boolean)}完成
     * 源码如下：
     * - final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
     * -   Node<K,V>[] tab; Node<K,V> p; int n, i;
     * -   if ((tab = table) == null || (n = tab.length) == 0) {
     * -     // 这里首先判断 table 是否是 null 是因为{@link HashMap#HashMap()}
     * -     n = (tab = resize()).length;
     * -   }
     * -   if ((p = tab[i = (n - 1) & hash]) == null)
     * -    tab[i] = newNode(hash, key, value, null);
     * -   else {
     * -     Node<K,V> e; K k;
     * -     if (p.hash == hash &&
     * -       ((k = p.key) == key || (key != null && key.equals(k))))
     * -       e = p;
     * -     else if (p instanceof TreeNode)
     * -       e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
     * -     else {
     * -       for (int binCount = 0; ; ++binCount) {
     * -         if ((e = p.next) == null) {
     * -           p.next = newNode(hash, key, value, null);
     * -           if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
     * -             treeifyBin(tab, hash);
     * -             break;
     * -           }
     * -           if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
     * -             break;
     * -             p = e;
     * -           }
     * -         }
     * -         if (e != null) { // existing mapping for key
     * -           V oldValue = e.value;
     * -           if (!onlyIfAbsent || oldValue == null)
     * -             e.value = value;
     * -           afterNodeAccess(e);
     * -           return oldValue;
     * -         }
     * -       }
     * -     ++modCount;
     * -   if (++size > threshold)
     * -     resize();
     * -   afterNodeInsertion(evict);
     * -   return null;
     * - }
     * <p>
     * 继承结构：
     *
     * @see Map
     * @see AbstractMap
     * @see Cloneable
     * @see java.io.Serializable
     */
    HashMap(HashMap.class) {
        @Override
        public Map get() {
            return new HashMap();
        }

        @Override
        public Map apply(int value) {
            return new HashMap(value);
        }

        @Override
        public Map apply(Map map) {
            return new HashMap(map);
        }
    },

    /**
     * 继承结构：
     *
     * @see Map
     * @see AbstractMap
     * @see HashMap
     * @see Cloneable
     * @see java.io.Serializable
     */
    LinkedHashMap(LinkedHashMap.class) {
        @Override
        public Map get() {
            return new LinkedHashMap();
        }

        @Override
        public Map apply(int value) {
            return new LinkedHashMap();
        }

        @Override
        public Map apply(Map map) {
            return new LinkedHashMap(map);
        }
    },

    /**
     * 继承结构
     *
     * @see Map
     * @see Dictionary
     * @see Cloneable
     * @see java.io.Serializable
     */
    Hashtable(Hashtable.class) {
        @Override
        public Map get() {
            return new Hashtable();
        }

        @Override
        public Map apply(int value) {
            return new Hashtable(value);
        }

        @Override
        public Map apply(Map map) {
            return new Hashtable(map);
        }
    },

    /**
     * 继承结构:
     *
     * @see Map
     * @see Dictionary
     * @see Cloneable
     * @see java.io.Serializable
     * @see Hashtable
     */
    Properties(Properties.class) {
        @Override
        public Map get() {
            return new Properties();
        }

        @Override
        public Map apply(int value) {
            return new Properties();
        }

        @Override
        public Map apply(Map map) {
            Properties properties = new Properties();
            if (map != null) {
                properties.putAll(map);
            }
            return properties;
        }
    },

    /**
     * 继承结构:
     *
     * @see AbstractMap
     * @see SortedMap
     * @see NavigableMap
     * @see Cloneable
     * @see java.io.Serializable
     */
    TreeMap(TreeMap.class) {
        @Override
        public Map get() {
            return new TreeMap();
        }

        @Override
        public Map apply(int value) {
            return new TreeMap();
        }

        @Override
        public Map apply(Map map) {
            return new TreeMap(map);
        }
    },

    /**
     * 继承结构：
     *
     * @see Map
     * @see AbstractMap
     * @see Cloneable
     * @see java.io.Serializable
     */
    IdentityHashMap(IdentityHashMap.class) {
        @Override
        public Map get() {
            return new IdentityHashMap();
        }

        @Override
        public Map apply(int value) {
            return new IdentityHashMap(value);
        }

        @Override
        public Map apply(Map map) {
            return new IdentityHashMap(map);
        }
    },

    /**
     * 继承结构：
     *
     * @see Map
     * @see AbstractMap
     */
    WeakHashMap(WeakHashMap.class) {
        @Override
        public Map get() {
            return new WeakHashMap();
        }

        @Override
        public Map apply(int value) {
            return new WeakHashMap(value);
        }

        @Override
        public Map apply(Map map) {
            return new WeakHashMap(map);
        }
    },

    /**
     * 继承结构：
     *
     * @see Map
     * @see AbstractMap
     * @see java.util.concurrent.ConcurrentMap
     * @see java.io.Serializable
     */
    ConcurrentHashMap(ConcurrentHashMap.class) {
        @Override
        public Map get() {
            return new ConcurrentHashMap();
        }

        @Override
        public Map apply(int value) {
            return new ConcurrentHashMap(value);
        }

        @Override
        public Map apply(Map map) {
            return new ConcurrentHashMap(map);
        }
    };

    private final Class type;

    static final class CtorCached {
        final static HashMap<Class, MapEnum> CACHE = new HashMap();
    }

    MapEnum(Class type) {
        CtorCached.CACHE.put(this.type = type, this);
    }

    public Class type() {
        return type;
    }
}

package com.moon.util;

import com.moon.beans.BeanInfoUtil;
import com.moon.beans.FieldDescriptor;
import com.moon.enums.ArraysEnum;
import com.moon.enums.CollectionEnum;
import com.moon.lang.EnumUtil;
import com.moon.lang.ThrowUtil;
import com.moon.lang.ref.IntAccessor;
import com.moon.util.function.*;
import com.moon.util.iterators.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.*;

import static com.moon.io.FileUtil.getFileInputStream;
import static com.moon.lang.ThrowUtil.throwRuntime;
import static com.moon.util.OptionalUtil.computeOrElse;
import static com.moon.util.iterators.NullIterator.Null;

/**
 * 通用迭代器
 *
 * @author benshaoye
 * @date 2018/9/11
 */
public final class IteratorUtil {

    private IteratorUtil() {
        ThrowUtil.noInstanceError();
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * array iterator
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 获取一个遍历指定类型数组的迭代器
     *
     * @param ts
     * @param <T>
     */
    public static <T> Iterator<T> of(T... ts) {
        return ts == null ? Null : new ObjectsIterator<>(ts);
    }

    /**
     * 获取 byte[] 类型数组的迭代器
     *
     * @param bytes
     */
    public static Iterator<Byte> of(byte[] bytes) {
        return computeOrElse(bytes, BytesIterator::new, Null);
    }

    /**
     * 获取 short[] 类型数组的迭代器
     *
     * @param shorts
     */
    public static Iterator<Short> of(short[] shorts) {
        return computeOrElse(shorts, ShortsIterator::new, Null);
    }

    /**
     * 获取 char[] 类型数组的迭代器
     *
     * @param chars
     */
    public static Iterator<Character> of(char[] chars) {
        return chars == null ? Null : new CharsIterator(chars);
    }

    /**
     * 获取 int[] 类型数组的迭代器
     *
     * @param ints
     */
    public static Iterator<Integer> of(int[] ints) {
        return computeOrElse(ints, IntsIterator::new, Null);
    }

    /**
     * 获取 long[] 类型数组的迭代器
     *
     * @param longs
     */
    public static Iterator<Long> of(long[] longs) {
        return computeOrElse(longs, LongsIterator::new, Null);
    }

    /**
     * 获取 float[] 类型数组的迭代器
     *
     * @param floats
     */
    public static Iterator<Float> of(float[] floats) {
        return computeOrElse(floats, FloatsIterator::new, Null);
    }

    /**
     * 获取 double[] 类型数组的迭代器
     *
     * @param doubles
     */
    public static Iterator<Double> of(double[] doubles) {
        return computeOrElse(doubles, DoublesIterator::new, Null);
    }

    /**
     * 获取 boolean[] 类型数组的迭代器
     *
     * @param booleans
     */
    public static Iterator<Boolean> of(boolean[] booleans) {
        return computeOrElse(booleans, BooleansIterator::new, Null);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * string iterator
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 返回字符串的字符迭代器
     *
     * @param string
     */
    public static Iterator<Character> of(CharSequence string) {
        return string == null ? Null : new CharsIterator(string);
    }

    /**
     * 返回文本行迭代器
     *
     * @param string
     */
    public static Iterator ofLines(CharSequence string) {
        return string == null ? Null : new TextReaderIterator(string);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * file line iterator
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 从 Reader 中每次读取一行文本
     *
     * @param reader
     * @return
     */
    public static Iterator<String> ofLines(Reader reader) {
        return reader == null ? Null : new TextReaderIterator(reader);
    }

    /**
     * 从 InputStream 中按默认字符编码（UTF-8）格式每次读取一行文本
     *
     * @param is
     * @return
     */
    public static Iterator<String> ofLines(InputStream is) {
        return is == null ? Null : new TextReaderIterator(is);
    }

    /**
     * 从 InputStream 中按 charset 格式每次读取一行文本
     *
     * @param is
     * @param charset
     * @return
     */
    public static Iterator<String> ofLines(InputStream is, String charset) {
        return is == null ? Null : new TextReaderIterator(is, charset);
    }

    /**
     * 从 InputStream 中按 charset 格式每次读取一行文本
     *
     * @param is
     * @param charset
     * @return
     */
    public static Iterator<String> ofLines(InputStream is, Charset charset) {
        return is == null ? Null : new TextReaderIterator(is, charset);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * io iterator
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 获取一个文本文件读取迭代器，可用于常用的txt、json、xml等文本文件读取；
     * 迭代器每次返回一行数据，直到文本结尾，对象会自动关闭文件流
     *
     * @param file
     */
    public static Iterator<String> ofLines(File file) {
        return file == null ? Null : new TextReaderIterator(file);
    }

    /**
     * 文件流迭代读取器
     * 每次将读取的字节放入数组 buffer 中，并返回读取到的长度
     *
     * @param filepath
     * @param buffer
     */
    public static Iterator<Integer> of(String filepath, byte[] buffer) {
        return filepath == null ? Null : new FileStreamIterator(filepath, buffer);
    }

    /**
     * 文件流迭代读取器
     * 每次将读取的字节放入数组 buffer 中，并返回读取到的长度
     *
     * @param file
     * @param buffer
     */
    public static Iterator<Integer> of(File file, byte[] buffer) {
        return file == null ? Null : new FileStreamIterator(file, buffer);
    }

    /**
     * 文件流迭代读取器
     * 每次将读取的字节放入数组 buffer 中，并返回读取到的长度
     *
     * @param inputStream
     * @param buffer
     */
    public static Iterator<Integer> of(InputStream inputStream, byte[] buffer) {
        return inputStream == null ? Null : new FileStreamIterator(inputStream, buffer);
    }

    /**
     * 将 JavaBean 中每个属性认为是一个字段进行迭代
     *
     * @param object
     * @return
     */
    public static Iterator<Map.Entry<String, FieldDescriptor>> ofBean(Object object) {
        return object == null ? Null : of(BeanInfoUtil.getFieldDescriptorsMap(object.getClass()));
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * set iterator
     * -----------------------------------------------------------------------------------------------------------
     */

    public static <T> Iterator<T> of(Iterator<T> iterator) {
        return iterator == null ? Null : iterator;
    }

    /**
     * 返回 List 集合的迭代器，此迭代器按 List 索引迭代
     *
     * @param list
     * @param <T>
     */
    public static <T> Iterator<T> of(List<T> list) {
        return list == null ? Null : list.iterator();
    }

    /**
     * 返回 Collection 集合的迭代器
     *
     * @param c
     * @param <T>
     */
    public static <T> Iterator<T> of(Collection<T> c) {
        return c == null ? Null : c.iterator();
    }

    /**
     * 返回 Iterable 集合的迭代器
     *
     * @param iterable
     * @param <T>
     */
    public static <T> Iterator<T> of(Iterable<T> iterable) {
        return iterable == null ? Null : iterable.iterator();
    }

    /**
     * 返回 Map 集合迭代器
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    public static <K, V> Iterator<Map.Entry<K, V>> of(Map<K, V> map) {
        return map == null ? Null : map.entrySet().iterator();
    }

    /**
     * 返回 Enumeration 迭代器
     *
     * @param enumeration
     * @param <T>
     */
    public static <T> Iterator<T> of(Enumeration<T> enumeration) {
        return enumeration == null ? Null : new EnumerationIterator<>(enumeration);
    }

    /**
     * 返回类字段信息描述迭代器;
     * 迭代器每次返回一个枚举；
     *
     * @param clazz
     * @param <T>
     */
    public static <T extends Enum<T>> Iterator<T> of(Class<T> clazz) {
        return clazz != null && clazz.isEnum() ? new ObjectsIterator(clazz.getEnumConstants()) : Null;
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * for each(object)
     * -----------------------------------------------------------------------------------------------------------
     */


    public final static void forEachAll(Object data, IntBiConsumer consumer) {
        if (data instanceof Iterable) {
            forEach((Iterable) data, consumer);
        } else if (data instanceof Map) {
            forEach(((Map) data).entrySet(), consumer);
        } else if (data instanceof Iterator) {
            forEach((Iterator) data, consumer);
        } else if (data == null) {
            return;
        }
        Class type = data.getClass();
        if (type.isArray()) {
            ArraysEnum.getOrObjects(data).forEach(data, consumer);
        } else {
            forEachBean(data, consumer);
        }
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * for each(JavaBean)
     * -----------------------------------------------------------------------------------------------------------
     */

    public final static void forEachBean(Object bean, IntBiConsumer consumer) {
        if (bean != null) {
            IntAccessor indexer = IntAccessor.of();
            BeanInfoUtil.getFieldDescriptorsMap(bean.getClass()).forEach((name, desc) ->
                consumer.accept(desc.getValueIfPresent(bean, true), indexer.getAndAdd()));
        }
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * array for each(item)
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(boolean[] array, BooleanConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(double[] array, DoubleConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(float[] array, FloatConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(long[] array, LongConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(int[] array, IntConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(char[] array, CharConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(short[] array, ShortConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @return
     */
    public static void forEach(byte[] array, ByteConsumer consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历，谨慎处理 string
     *
     * @param consumer 处理对象
     * @param array
     * @param <T>
     */
    public static <T extends CharSequence> void forEach(T[] array, Consumer<T> consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param array
     * @param <T>
     */
    public static <T> void forEach(T[] array, Consumer<T> consumer) {
        int length = array == null ? 0 : array.length;
        for (int i = 0; i < length; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 遍历枚举类
     *
     * @param enumType
     * @param consumer
     * @param <T>
     */
    public static <T extends Enum<T>> void forEach(Class<T> enumType, Consumer<T> consumer) {
        forEach(EnumUtil.values(enumType), consumer);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * array for each count
     * -----------------------------------------------------------------------------------------------------------
     */

    public static void forEach(final int count, IntConsumer consumer) {
        for (int i = 0; i < count; i++) {
            consumer.accept(i);
        }
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * array for each(item, index)
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 遍历处理
     *
     * @param array
     * @param consumer 处理对象
     * @return
     */
    public static void forEach(int[] array, IntIntConsumer consumer) {
        for (int i = 0, length = array == null ? 0 : array.length; i < length; i++) {
            consumer.accept(array[i], i);
        }
    }

    /**
     * 遍历处理
     *
     * @param array
     * @param consumer 处理对象
     * @return
     */
    public static void forEach(long[] array, IntLongConsumer consumer) {
        for (int i = 0, length = array == null ? 0 : array.length; i < length; i++) {
            consumer.accept(array[i], i);
        }
    }

    /**
     * 遍历处理
     *
     * @param array
     * @param consumer 处理对象
     * @return
     */
    public static void forEach(double[] array, IntDoubleConsumer consumer) {
        for (int i = 0, length = array == null ? 0 : array.length; i < length; i++) {
            consumer.accept(array[i], i);
        }
    }

    /**
     * 遍历处理
     *
     * @param array
     * @param consumer 处理对象
     * @return
     */
    public static <T extends CharSequence> void forEach(T[] array, IntBiConsumer<T> consumer) {
        for (int i = 0, length = array == null ? 0 : array.length; i < length; i++) {
            consumer.accept(array[i], i);
        }
    }

    /**
     * 遍历处理
     *
     * @param array
     * @param consumer 处理对象
     * @return
     */
    public static <T> void forEach(T[] array, IntBiConsumer<T> consumer) {
        for (int i = 0, length = array == null ? 0 : array.length; i < length; i++) {
            consumer.accept(array[i], i);
        }
    }

    /**
     * 遍历枚举类
     *
     * @param enumType
     * @param consumer
     * @param <T>
     */
    public static <T extends Enum<T>> void forEach(Class<T> enumType, IntBiConsumer<T> consumer) {
        forEach(EnumUtil.values(enumType), consumer);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * set for each
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 遍历
     *
     * @param consumer 处理对象
     * @param list
     * @param <T>
     * @return
     */
    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        if (list != null) {
            list.forEach(consumer);
        }
    }

    /**
     * 遍历 Collection
     *
     * @param consumer 处理对象
     * @param c
     * @param <T>
     * @return
     */
    public static <T> void forEach(Collection<T> c, Consumer<T> consumer) {
        if (c != null) {
            c.forEach(consumer);
        }
    }

    /**
     * 遍历 Iterable
     *
     * @param consumer 处理对象
     * @param c
     * @param <T>
     * @return
     */
    public static <T> void forEach(Iterable<T> c, Consumer<T> consumer) {
        if (c != null) {
            c.forEach(consumer);
        }
    }

    public static <T> void forEach(Iterable<T> list, IntBiConsumer<T> consumer) {
        if (list != null) {
            int i = 0;
            for (T item : list) {
                consumer.accept(item, i);
                i++;
            }
        }
    }

    /**
     * 遍历
     *
     * @param i
     * @param consumer
     * @param <T>
     */
    public static <T> void forEach(Iterator<T> i, Consumer<T> consumer) {
        if (i != null) {
            i.forEachRemaining(consumer);
        }
    }

    public static <T> void forEach(Iterator<T> i, IntBiConsumer<T> consumer) {
        if (i != null) {
            for (int idx = 0; i.hasNext(); idx++) {
                consumer.accept(i.next(), idx);
            }
        }
    }

    /**
     * 遍历 Map
     *
     * @param consumer 处理对象
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> void forEach(Map<K, V> map, Consumer<Map.Entry<K, V>> consumer) {
        if (map != null) {
            map.entrySet().forEach(consumer);
        }
    }

    /**
     * 遍历 Map
     *
     * @param consumer 处理对象
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> void forEach(Map<K, V> map, BiConsumer<K, V> consumer) {
        if (map != null) {
            map.forEach(consumer);
        }
    }

    /**
     * 遍历 Enumeration
     *
     * @param consumer 处理对象
     * @param e
     * @param <T>
     * @return
     */
    public static <T> void forEach(Enumeration<T> e, Consumer<T> consumer) {
        if (e != null) {
            while (e.hasMoreElements()) {
                consumer.accept(e.nextElement());
            }
        }
    }


    /*
     * -----------------------------------------------------------------------------------------------------------
     * io for each
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 遍历处理文本文件每一行数据
     *
     * @param consumer 处理对象
     * @param file
     */
    public static void forEach(File file, Consumer<String> consumer) {
        ofLines(file).forEachRemaining(consumer);
    }

    /**
     * 文件流读取和处理
     *
     * @param consumer 处理对象，接受一个参数，代表每次读取 byte 长度
     * @param filepath
     * @param buffer
     */
    public static void forEach(String filepath, byte[] buffer, IntConsumer consumer) {
        forEach(getFileInputStream(filepath), buffer, consumer);
    }

    /**
     * 文件流读取和处理
     *
     * @param consumer 处理对象，接受一个参数，代表每次读取 byte 长度
     * @param file
     * @param buffer
     */
    public static void forEach(File file, byte[] buffer, IntConsumer consumer) {
        forEach(getFileInputStream(file), buffer, consumer);
    }

    public static void forEach(Reader reader, Consumer<String> consumer) {
        ofLines(reader).forEachRemaining(consumer);
    }

    public static void forEach(Reader reader, char[] buffer, Consumer<Integer> consumer) {
        if (reader != null) {
            try {
                int length = buffer.length;
                int limit = reader.read(buffer, 0, length);
                while (limit >= 0) {
                    consumer.accept(limit);
                    limit = reader.read(buffer, 0, length);
                }
            } catch (IOException e) {
                throwRuntime(e);
            }
        }
    }

    /**
     * 流读取和处理
     *
     * @param consumer    处理对象
     * @param inputStream
     * @param buffer
     */
    public static void forEach(InputStream inputStream, byte[] buffer, IntConsumer consumer) {
        try {
            final int length = buffer.length;
            boolean whiling;
            int limit;
            do {
                limit = inputStream.read(buffer, 0, length);
                if (whiling = (limit >= 0)) {
                    consumer.accept(limit);
                }
            } while (whiling);
        } catch (Exception e) {
            throwRuntime(e);
        }
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * split
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 集合拆分器
     * 默认拆分后每个容器里有十六个元素
     *
     * @param c
     * @param <E>
     * @param <C>
     * @return
     */
    public static <E, C extends Collection<E>> Iterator<C> split(C c) {
        return c == null ? Null : new CollectionSplitter<>(c);
    }

    /**
     * 集合拆分器
     * 将集合拆分成指定大小的若干个相同类型集合;
     * 不足个数的统一放入最后一个集合
     * 默认拆分后每个容器里有十六个元素
     *
     * @param c
     * @param size 指定拆分大小
     * @param <E>
     * @param <C>
     * @return
     */
    public static <E, C extends Collection<E>> Iterator<C> split(C c, int size) {
        return c == null ? Null : new CollectionSplitter<>(c, size);
    }

    /**
     * 集合拆分处理器
     *
     * @param c
     * @param size
     * @param consumer
     * @param <E>
     * @param <C>
     */
    public static <E, C extends Collection<E>> void splitter(C c, int size, Consumer<? super Collection<E>> consumer) {
        split(c, size).forEachRemaining(consumer);
    }

    /**
     * 集合拆分处理器
     *
     * @param c
     * @param consumer
     * @param <E>
     * @param <C>
     */
    public static <E, C extends Collection<E>> void splitter(C c, Consumer<? super Collection<E>> consumer) {
        split(c).forEachRemaining(consumer);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * group by
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * 集合分组
     *
     * @param list     集合
     * @param function 分组键
     * @param <K>      键类型
     * @param <E>      集合单项类型
     * @param <L>      List 类型
     * @return
     */
    public static <K, E, L extends List<E>> Map<K, L> groupBy(L list, Function<E, K> function) {
        final Supplier supplier = CollectionEnum.getOrDefault(list, CollectionEnum.ArrayList);
        return groupBy(list, function, supplier);
    }

    public static <K, E, S extends Set<E>> Map<K, S> groupBy(S set, Function<E, K> function) {
        final Supplier supplier = CollectionEnum.getOrDefault(set, CollectionEnum.HashSet);
        return groupBy(set, function, supplier);
    }

    public static <K, E, C extends Collection<E>> Map<K, C> groupBy(C collect, Function<E, K> function) {
        final Supplier supplier = CollectionEnum.getOrDefault(collect, CollectionEnum.HashSet);
        return groupBy(collect, function, supplier);
    }

    public static <K, E, C extends Collection<E>, CR extends Collection<E>>

    Map<K, CR> groupBy(C collect, Function<E, K> function, Supplier<CR> groupingSupplier) {
        Map<K, CR> grouped = new HashMap<>();
        if (collect != null) {
            for (E item : collect) {

                K key = function.apply(item);
                CR grouping = grouped.get(key);
                if (grouping == null) {
                    grouped.put(key, grouping = groupingSupplier.get());
                }
                grouping.add(item);
            }
        }
        return grouped;
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * filter
     * -----------------------------------------------------------------------------------------------------------
     */

    /**
     * @param list
     * @param tester
     * @param <E>
     * @param <L>
     * @return
     */
    public static <E, L extends List<E>> List<E> filter(L list, Predicate<E> tester) {
        return FilterUtil.filter(list, tester);
    }

    public static <E, S extends Set<E>> Set<E> filter(S set, Predicate<E> tester) {
        return FilterUtil.filter(set, tester);
    }

    /**
     * @param collect
     * @param tester
     * @param resultContainerSupplier 符合过滤条件项容器构造器
     * @param <E>
     * @param <C>
     * @param <CR>
     * @return
     */
    public static <E, C extends Collection<E>, CR extends Collection<E>>

    CR filter(C collect, Predicate<E> tester, Supplier<CR> resultContainerSupplier) {
        return FilterUtil.filter(collect, tester, resultContainerSupplier);
    }

    /**
     * @param collect
     * @param tester
     * @param toResultContainer 符合过滤条件的容器
     * @param <E>
     * @param <C>
     * @param <CR>
     * @return 返回提供的容器 toResultContainer
     */
    public static <E, C extends Collection<E>, CR extends Collection<E>>

    CR filter(C collect, Predicate<E> tester, CR toResultContainer) {
        return FilterUtil.filter(collect, tester, toResultContainer);
    }

    /*
     * -----------------------------------------------------------------------------------------------------------
     * map
     * -----------------------------------------------------------------------------------------------------------
     */

    public static <E, T, L extends List<E>> List<T> map(L list, Function<E, T> function) {
        final IntFunction supplier = CollectionEnum.getOrDefault(list, CollectionEnum.ArrayList);
        return (List) map(list, function, supplier);
    }

    public static <E, T, S extends Set<E>> Set<T> map(S set, Function<E, T> function) {
        final IntFunction supplier = CollectionEnum.getOrDefault(set, CollectionEnum.HashSet);
        return (Set) map(set, function, supplier);
    }

    public static <E, T, C extends Collection<E>, CR extends Collection<T>> CR map(C collect, Function<E, T> function) {
        final IntFunction supplier = CollectionEnum.getOrDefault(collect, CollectionEnum.HashSet);
        return (CR) map(collect, function, supplier);
    }

    public static <E, T, C extends Collection<E>, CR extends Collection<T>>

    CR map(C collect, Function<E, T> function, IntFunction<CR> containerSupplier) {
        return map(collect, function, containerSupplier.apply(collect == null ? 0 : collect.size()));
    }

    public static <E, T, C extends Collection<E>, CR extends Collection<T>>

    CR map(C collect, Function<E, T> function, CR container) {
        if (collect != null) {
            for (E item : collect) {
                container.add(function.apply(item));
            }
        }
        return container;
    }
}

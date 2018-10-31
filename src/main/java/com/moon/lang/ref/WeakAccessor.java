package com.moon.lang.ref;

import java.lang.ref.Reference;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.moon.lang.ObjectUtil.defaultIfNull;

/**
 * 缓存一个访问过程
 * 此类的作用是将一个对象的返回值缓存起来，
 * 缓存的对象不保证一直存在，在适当的时候可能会被回收
 * 但当再一次访问的时候，会去执行这个过程，再一次获得这个值并缓存
 * 频繁访问的时候有助于提高速度。
 * 传统的单例模式主要分为饿汉模式和懒汉模式，以及利用方法锁、对象锁、类加载机制或枚举及其衍生方法，
 * 但是这些模式在处理时间和空间的关系上还有一点缺憾，对于小对象，简单值等
 * 直接创建一个静态实例，无论什么模式，所占用的空间是可以接受的，同样对于单纯的大对象，
 * 无论是类加载时加载还是使用时加载，都会一直存在，时间和空间的协调上有不合适的地方。
 * 此类主要针对在一定时期内频繁访问，而其他时候并不重要的对象，这时候我们可以将这个过程
 * 缓存，使用完毕之后内存空间仍然可能会被释放。
 *
 * @author benshaoye
 * @date 2018/9/11
 */
public class WeakAccessor<T> {
    /**
     * 缓存取值过程
     */
    private final Supplier<T> supplier;
    /**
     * 虚缓存对象
     */
    private Reference<T> reference;

    public WeakAccessor(Supplier<T> supplier) {
        this.supplier = supplier;
        syncNewLoad();
    }

    public static <T> WeakAccessor<T> of(Supplier<T> supplier) {
        return new WeakAccessor<>(supplier);
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    /**
     * 获取值
     *
     * @return
     */
    public T get() {
        return reference.get();
    }

    public T getOrDefault(T obj) {
        return defaultIfNull(get(), obj);
    }

    public T getOrDefault(Supplier<T> supplier) {
        T curr = get();
        return curr == null ? supplier.get() : curr;
    }

    public T getOrReload() {
        T curr = get();
        if (curr == null) {
            curr = syncNewLoad();
        }
        return curr;
    }

    /*
     * ------------------------------------------------------------
     * assertions
     * ------------------------------------------------------------
     */

    public boolean isPresent() {
        return get() != null;
    }

    public boolean isAbsent() {
        return get() == null;
    }

    public boolean isEquals(Object obj) {
        return Objects.equals(obj, getOrReload());
    }

    /*
     * ------------------------------------------------------------
     * consumers
     * ------------------------------------------------------------
     */

    public WeakAccessor<T> ifPresent(Consumer<T> consumer) {
        T curr = get();
        if (curr != null) {
            consumer.accept(curr);
        }
        return this;
    }

    public WeakAccessor<T> ifPresentOrThrow(Consumer<T> consumer) {
        T curr = get();
        if (curr != null) {
            consumer.accept(curr);
            return this;
        }
        throw new NullPointerException();
    }

    public WeakAccessor<T> ifPresentOrThrow(Consumer<T> consumer, String message) {
        T curr = get();
        if (curr != null) {
            consumer.accept(curr);
            return this;
        }
        throw new NullPointerException(message);
    }

    public WeakAccessor<T> ifPresentOrReload(Consumer<T> consumer) {
        T curr = getOrReload();
        if (curr != null) {
            consumer.accept(curr);
        }
        return this;
    }


    /*
    inner
     */

    T syncNewLoad() {
        T t;
        if (reference == null) {
            synchronized (this) {
                if (reference == null || (t = reference.get()) == null) {
                    t = cache(supplier.get());
                }
            }
        } else if ((t = reference.get()) == null) {
            synchronized (this) {
                t = cache(supplier.get());
            }
        }

        if (t == null) {
            throw new IllegalArgumentException("返回值不能为 null : " + supplier);
        }
        return t;
    }

    private T cache(T value) {
        reference = ReferenceUtil.weak(value);
        return value;
    }
}

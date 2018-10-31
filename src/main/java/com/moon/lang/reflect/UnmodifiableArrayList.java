package com.moon.lang.reflect;

import com.moon.util.Unmodifiable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * @author ZhangDongMin
 * @date 2018/9/11
 */
class UnmodifiableArrayList<T> extends ArrayList<T>
    implements List<T>, RandomAccess, Cloneable,
    java.io.Serializable, Unmodifiable<T> {

    private static final long serialVersionUID = 868345258112289218L;

    private boolean canModify = true;

    UnmodifiableArrayList() {
        super();
    }

    UnmodifiableArrayList(Collection<T> collection) {
        super(collection);
    }

    UnmodifiableArrayList(T[] elementData) {
        super(elementData.length);
        for (T item : elementData) {
            add(item);
        }
    }

    static <T> UnmodifiableArrayList<T> unmodifiable(T[] elementData) {
        return new UnmodifiableArrayList(elementData).unmodifiable();
    }

    @Override
    public boolean add(T t) {
        if (canModify) {
            return super.add(t);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public T set(int index, T element) {
        if (canModify) {
            return super.set(index, element);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void add(int index, T element) {
        if (canModify) {
            super.add(index, element);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public T remove(int index) {
        if (canModify) {
            return super.remove(index);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    @Override
    public void clear() {
        if (canModify) {
            super.clear();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (canModify) {
            return super.addAll(index, c);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size()) {
                throw new NoSuchElementException();
            }
            cursor = i + 1;
            return UnmodifiableArrayList.this.get(i);
        }

        @Override
        public void remove() {
            if (lastRet < 0 || !canModify) {
                throw new IllegalStateException();
            }
            checkForComodification();

            try {
                UnmodifiableArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return super.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return super.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new UnmodifiableArrayList<>(super.subList(fromIndex, toIndex)).unmodifiable();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (canModify) {
            super.removeRange(fromIndex, toIndex);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public Object[] toArray() {
        return super.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return super.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        if (canModify) {
            return super.remove(o);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (canModify) {
            return super.addAll(c);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (canModify) {
            return super.removeAll(c);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (canModify) {
            return super.retainAll(c);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        if (canModify) {
            super.replaceAll(operator);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void sort(Comparator<? super T> c) {
        if (canModify) {
            super.sort(c);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return super.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        if (canModify) {
            return super.removeIf(filter);
        } else {
            return false;
        }
    }

    @Override
    public Stream<T> stream() {
        if (canModify) {
            return super.stream();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Stream<T> parallelStream() {
        if (canModify) {
            return super.parallelStream();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    @Override
    public UnmodifiableArrayList<T> unmodifiable() {
        this.canModify = false;
        return this;
    }

    @Override
    public int size() {
        return super.size();
    }
}

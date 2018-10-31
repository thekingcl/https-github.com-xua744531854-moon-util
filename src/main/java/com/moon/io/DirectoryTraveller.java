package com.moon.io;

import com.moon.util.IteratorUtil;

import java.io.File;
import java.util.*;

/**
 * 此方法支持遍历多个文件夹，然后一次性返回所有文件列表
 *
 * @author benshaoye
 * @date 2018/9/11
 */
public class DirectoryTraveller
    implements Traveller<File>, List<File> {

    private List<File> files;

    private final boolean ignoreSecurity;

    public DirectoryTraveller() {
        this(true);
    }

    public DirectoryTraveller(boolean ignoreSecurity) {
        this.files = new ArrayList<>();
        this.ignoreSecurity = ignoreSecurity;
    }

    @Override
    public DirectoryTraveller traverse(String dirPath) {
        traverse(new File(dirPath));
        return this;
    }

    @Override
    public DirectoryTraveller traverse(File dirFile) {
        if (dirFile.exists()) {
            try {
                if (dirFile.isDirectory()) {
                    for (Iterator<String> i = IteratorUtil.of(dirFile.list()); i.hasNext(); ) {
                        traverse(new File(dirFile, i.next()));
                    }
                } else if (dirFile.isFile()) {
                    files.add(dirFile);
                }
            } catch (SecurityException e) {
                if (!ignoreSecurity) {
                    throw new IllegalArgumentException(dirFile.getAbsolutePath(), e);
                }
            }
        }
        return this;
    }

    /**
     * Returns the number of value in this data.  If this data contains
     * more than <tt>Integer.MAX_VALUE</tt> value, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of value in this data
     */
    @Override
    public int size() {
        return this.files.size();
    }

    /**
     * Returns <tt>true</tt> if this data contains no value.
     *
     * @return <tt>true</tt> if this data contains no value
     */
    @Override
    public boolean isEmpty() {
        return this.files.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this data contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this data contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this data is to be tested
     * @return <tt>true</tt> if this data contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this data
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              data does not permit null value
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        return this.files.contains(o);
    }

    /**
     * Returns an iterator over the value in this data in proper sequence.
     *
     * @return an iterator over the value in this data in proper sequence
     */

    @Override
    public Iterator<File> iterator() {
        return this.files.iterator();
    }

    /**
     * Returns an array containing all of the value in this data in proper
     * sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this data.  (In other words, this method must
     * allocate a new array even if this data is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the value in this data in proper
     * sequence
     * @see Arrays#asList(Object[])
     */

    @Override
    public Object[] toArray() {
        return this.files.toArray();
    }

    /**
     * Returns an array containing all of the value in this data in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the data fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this data.
     *
     * <p>If the data fits in the specified array with room to spare (i.e.,
     * the array has more value than the data), the element in the array
     * immediately following the end of the data is set to <tt>null</tt>.
     * (This is useful in determining the length of the data <i>only</i> if
     * the caller knows that the data does not contain any null value.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose <tt>x</tt> is a data known to contain only strings.
     * The following code can be used to dump the data into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the value of this data are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the value of this data
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this data
     * @throws NullPointerException if the specified array is null
     */

    @Override
    public <T> T[] toArray(T[] a) {
        return this.files.toArray(a);
    }

    /**
     * Appends the specified element to the end of this data (optional
     * operation).
     *
     * <p>Lists that support this operation may place limitations on what
     * value may be added to this data.  In particular, some
     * lists will refuse to addNonNull null value, and others will impose
     * restrictions on the type of value that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what value may be added.
     *
     * @param file element to be appended to this data
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>addNonNull</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this data
     * @throws NullPointerException          if the specified element is null and this
     *                                       data does not permit null value
     * @throws IllegalArgumentException      if some property of this element
     *                                       prevents it from being added to this data
     */
    @Override
    public boolean add(File file) {
        this.traverse(file);
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this data,
     * if it is present (optional operation).  If this data does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest get <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;getOrLoad(i)==null&nbsp;:&nbsp;o.equals(getOrLoad(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this data
     * contained the specified element (or equivalently, if this data changed
     * as a result of the call).
     *
     * @param o element to be removed from this data, if present
     * @return <tt>true</tt> if this data contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this data
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       data does not permit null value
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this data
     */
    @Override
    public boolean remove(Object o) {
        return this.files.remove(o);
    }

    /**
     * Returns <tt>true</tt> if this data contains all of the value of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this data
     * @return <tt>true</tt> if this data contains all of the value of the
     * specified collection
     * @throws ClassCastException   if the types of one or more value
     *                              in the specified collection are incompatible with this
     *                              data
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null value and this data does not permit null
     *                              value
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return this.files.containsAll(c);
    }

    /**
     * Appends all of the value in the specified collection to the end of
     * this data, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation isEquals undefined if the specified collection isEquals modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this data, and it's nonempty.)
     *
     * @param c collection containing value to be added to this data
     * @return <tt>true</tt> if this data changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this data
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null value and this data does not permit null
     *                                       value, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this data
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection<? extends File> c) {
        if (c != null) {
            for (File file : c) {
                this.traverse(file);
            }
        }
        return true;
    }

    /**
     * Inserts all of the value in the specified collection into this
     * data at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * value to the right (increases their indices).  The new value
     * will appear in this data in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this data, and it's nonempty.)
     *
     * @param index get at which to insert the first element from the
     *              specified collection
     * @param c     collection containing value to be added to this data
     * @return <tt>true</tt> if this data changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this data
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null value and this data does not permit null
     *                                       value, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this data
     * @throws IndexOutOfBoundsException     if the get is out of range
     *                                       (<tt>get &lt; 0 || get &gt; size()</tt>)
     */
    @Override
    public boolean addAll(int index, Collection<? extends File> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes from this data all of its value that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing value to be removed from this data
     * @return <tt>true</tt> if this data changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of an element of this data
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this data contains a null element and the
     *                                       specified collection does not permit null value
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return this.files.removeAll(c);
    }

    /**
     * Retains only the value in this data that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this data all of its value that are not contained in the
     * specified collection.
     *
     * @param c collection containing value to be retained in this data
     * @return <tt>true</tt> if this data changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of an element of this data
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this data contains a null element and the
     *                                       specified collection does not permit null value
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return this.files.retainAll(c);
    }

    /**
     * 初始化或重置
     *
     * @return
     */
    @Override
    public void clear() {
        this.files.clear();
    }

    /**
     * Returns the element at the specified position in this data.
     *
     * @param index get of the element to return
     * @return the element at the specified position in this data
     * @throws IndexOutOfBoundsException if the get is out of range
     *                                   (<tt>get &lt; 0 || get &gt;= size()</tt>)
     */
    @Override
    public File get(int index) {
        return this.files.get(index);
    }

    /**
     * Replaces the element at the specified position in this data with the
     * specified element (optional operation).
     *
     * @param index   get of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>set</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this data
     * @throws NullPointerException          if the specified element is null and
     *                                       this data does not permit null value
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this data
     * @throws IndexOutOfBoundsException     if the get is out of range
     *                                       (<tt>get &lt; 0 || get &gt;= size()</tt>)
     */
    @Override
    public File set(int index, File element) {
        this.add(index, element);
        return this.get(index);
    }

    /**
     * Inserts the specified element at the specified position in this data
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent value to the right (adds one to their
     * indices).
     *
     * @param index   get at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>addNonNull</tt> operation
     *                                       is not supported by this data
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this data
     * @throws NullPointerException          if the specified element is null and
     *                                       this data does not permit null value
     * @throws IllegalArgumentException      if some property of the specified
     *                                       element prevents it from being added to this data
     * @throws IndexOutOfBoundsException     if the get is out of range
     *                                       (<tt>get &lt; 0 || get &gt; size()</tt>)
     */
    @Override
    public void add(int index, File element) {
        this.traverse(element);
    }

    /**
     * Removes the element at the specified position in this data (optional
     * operation).  Shifts any subsequent value to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * data.
     *
     * @param index the get of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this data
     * @throws IndexOutOfBoundsException     if the get is out of range
     *                                       (<tt>get &lt; 0 || get &gt;= size()</tt>)
     */
    @Override
    public File remove(int index) {
        return this.files.remove(index);
    }

    /**
     * Returns the get of the first occurrence of the specified element
     * in this data, or -1 if this data does not contain the element.
     * More formally, returns the lowest get <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;getOrLoad(i)==null&nbsp;:&nbsp;o.equals(getOrLoad(i)))</tt>,
     * or -1 if there is no such get.
     *
     * @param o element to search for
     * @return the get of the first occurrence of the specified element in
     * this data, or -1 if this data does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this data
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              data does not permit null value
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        return this.files.indexOf(o);
    }

    /**
     * Returns the get of the last occurrence of the specified element
     * in this data, or -1 if this data does not contain the element.
     * More formally, returns the highest get <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;getOrLoad(i)==null&nbsp;:&nbsp;o.equals(getOrLoad(i)))</tt>,
     * or -1 if there is no such get.
     *
     * @param o element to search for
     * @return the get of the last occurrence of the specified element in
     * this data, or -1 if this data does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this data
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              data does not permit null value
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        return this.files.lastIndexOf(o);
    }

    /**
     * Returns a data iterator over the value in this data (in proper
     * sequence).
     *
     * @return a data iterator over the value in this data (in proper
     * sequence)
     */

    @Override
    public ListIterator<File> listIterator() {
        return this.files.listIterator();
    }

    /**
     * Returns a data iterator over the value in this data (in proper
     * sequence), starting at the specified position in the data.
     * The specified get indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified get minus one.
     *
     * @param index get of the first element to be returned from the
     *              data iterator (by a call to {@link ListIterator#next next})
     * @return a data iterator over the value in this data (in proper
     * sequence), starting at the specified position in the data
     * @throws IndexOutOfBoundsException if the get is out of range
     *                                   ({@code get < 0 || get > size()})
     */

    @Override
    public ListIterator<File> listIterator(int index) {
        return this.files.listIterator(index);
    }

    /**
     * Returns a view of the portion of this data between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned data is
     * empty.)  The returned data is backed by this data, so non-structural
     * changes in the returned data are reflected in this data, and vice-versa.
     * The returned data supports all of the optional data operations supported
     * by this data.<p>
     * <p>
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a data can be used as a range operation by passing a subList view
     * instead of a whole data.  For example, the following idiom
     * removes a range of value from a data:
     * <pre>{@code
     *      data.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subList.<p>
     * <p>
     * The semantics of the data returned by this method become undefined if
     * the backing data (i.e., this data) is <i>structurally modified</i> in
     * any way other than via the returned data.  (Structural modifications are
     * those that change the size of this data, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this data
     * @throws IndexOutOfBoundsException for an illegal endpoint get value
     *                                   (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *                                   fromIndex &gt; toIndex</tt>)
     */

    @Override
    public List<File> subList(int fromIndex, int toIndex) {
        return this.files.subList(fromIndex, toIndex);
    }

    @Override
    public List<File> get() {
        return this.files;
    }
}

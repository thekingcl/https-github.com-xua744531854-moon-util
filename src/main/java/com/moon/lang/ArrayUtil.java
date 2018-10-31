package com.moon.lang;

import com.moon.lang.support.ArraySupport;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public final class ArrayUtil {
    private ArrayUtil() {
        noInstanceError();
    }

    public final static Class getArrayType(Class componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    /**
     * 将 fromChars 从 fromIndex 到 toIndex 的数据到 toChars 从 begin 开始的下标里
     *
     * @param fromChars
     * @param fromIndex
     * @param toIndex
     * @param toChars
     * @param begin
     * @return toChars
     * @throws NullPointerException 如果 CHARS 或 to 是 null 时
     */
    public static char[] copyOfRangeTo(char[] fromChars, int fromIndex, int toIndex, char[] toChars, int begin) {
        return ArraySupport.copyOfRangeTo(fromChars, fromIndex, toIndex, toChars, begin, true);
    }

    /*
     * ----------------------------------------------------------------
     * length
     * ----------------------------------------------------------------
     */

    public static int length(Object[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(boolean[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(double[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(float[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(long[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(int[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(short[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(byte[] arr) {
        return arr == null ? 0 : arr.length;
    }

    public static int length(char[] arr) {
        return arr == null ? 0 : arr.length;
    }

    /*
     * ----------------------------------------------------------------
     * fill
     * ----------------------------------------------------------------
     */

    public static <T> T[] fillFrom(T[] arr, int fromIndex, T value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static boolean[] fillFrom(boolean[] arr, int fromIndex, boolean value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static double[] fillFrom(double[] arr, int fromIndex, double value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static float[] fillFrom(float[] arr, int fromIndex, float value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static long[] fillFrom(long[] arr, int fromIndex, long value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static int[] fillFrom(int[] arr, int fromIndex, int value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static short[] fillFrom(short[] arr, int fromIndex, short value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static byte[] fillFrom(byte[] arr, int fromIndex, byte value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    public static char[] fillFrom(char[] arr, int fromIndex, char value) {
        Arrays.fill(arr, fromIndex, length(arr), value);
        return arr;
    }

    /*
     * ----------------------------------------------------------------
     * fill to
     * ----------------------------------------------------------------
     */

    public static <T> T[] fillTo(T[] arr, int toIndex, T value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static boolean[] fillTo(boolean[] arr, int toIndex, boolean value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static double[] fillTo(double[] arr, int toIndex, double value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static float[] fillTo(float[] arr, int toIndex, float value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static long[] fillTo(long[] arr, int toIndex, long value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static int[] fillTo(int[] arr, int toIndex, int value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static short[] fillTo(short[] arr, int toIndex, short value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static byte[] fillTo(byte[] arr, int toIndex, byte value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    public static char[] fillTo(char[] arr, int toIndex, char value) {
        Arrays.fill(arr, 0, toIndex, value);
        return arr;
    }

    /*
     * ----------------------------------------------------------------
     * remove index
     * ----------------------------------------------------------------
     */

    public static <T> T[] removeIndex(T[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = null;
        return arr;
    }

    public static boolean[] removeIndex(boolean[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = false;
        return arr;
    }

    public static double[] removeIndex(double[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static float[] removeIndex(float[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static long[] removeIndex(long[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static int[] removeIndex(int[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static short[] removeIndex(short[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static byte[] removeIndex(byte[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    public static char[] removeIndex(char[] arr, int index) {
        arr[ArraySupport.removeIndex(arr, arr.length, index)] = 0;
        return arr;
    }

    /*
     * ----------------------------------------------------------------
     * remove index : 总是返回一个新数组
     * ----------------------------------------------------------------
     */

    public static <T> T[] splice(T[] arr, int fromIndex, int count, T... elements) {
        return arr;
    }

    private static boolean[] splice(boolean[] arr, int fromIndex, int count, boolean... elements) {
        int l1 = arr.length, l2 = elements.length, l3 = fromIndex, l4;
        Object result;
        if (count == 0) {
            l4 = l1 - l3;
            result = new boolean[l1 + l2];
        } else {
            l3 = fromIndex + count;
            l4 = l1 - l3;
            if (l3 > l1 || (l4 == 0 && fromIndex > 0)) {
                throw new ArrayIndexOutOfBoundsException(l3 - 1);
            }
            result = new boolean[l2 + l4 + fromIndex];
        }
        System.arraycopy(arr, 0, result, 0, fromIndex);
        System.arraycopy(elements, 0, result, fromIndex, l2);
        System.arraycopy(arr, l3, result, fromIndex + l2, l1 - l4);
        return (boolean[]) result;
    }
}

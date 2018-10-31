package com.moon.lang.support;

import com.moon.lang.ThrowUtil;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public final class ArraySupport {
    private static final char[] EMPTY_CHARS = {};

    private ArraySupport() {
        ThrowUtil.noInstanceError();
    }

    public static char[] copyOfRangeTo(
        char[] chars,
        int fromIndex,
        int toIndex,
        char[] to,
        int distPos,
        boolean newIfNeed) {

        if (newIfNeed) {
            to = to == null ? new char[0] : to;
        }

        final int l1 = toIndex - fromIndex, l2 = to.length, l3 = l1 + distPos;
        if (l3 > l2) {
            if (newIfNeed) {
                char[] now = new char[l3];
                System.arraycopy(to, 0, now, 0, distPos);
                to = now;
            } else {
                String msg = "new length: " + l3 + ", current length: " + l2;
                throw new ArrayIndexOutOfBoundsException(msg);
            }
        }
        System.arraycopy(chars, fromIndex, to, distPos, l1);
        return to;
    }

    public static int removeIndex(Object arr, int length, int index) {
        int laseIndex = length - 1;
        System.arraycopy(arr, index + 1, arr, index, laseIndex - index);
        return laseIndex;
    }

    public static int splice(){
        return 0;
    }
}

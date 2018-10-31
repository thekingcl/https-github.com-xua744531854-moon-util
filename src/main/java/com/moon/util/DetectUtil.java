package com.moon.util;

import com.moon.lang.ThrowUtil;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class DetectUtil {
    private DetectUtil() {
        ThrowUtil.noInstanceError();
    }

    /**
     * 是否是一个全是数字的字符串（即正整数）
     *
     * @param string
     */
    public static boolean isNumeric(String string) {
        int len = string == null ? 0 : (string = string.trim()).length();
        if (len > 0) {
            char ch;
            for (int i = 0; i < len; i++) {
                ch = string.charAt(i);
                if (ch > 57 || ch < 48) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 是否是一个整数（包括正整数和负整数，只支持十进制数）
     *
     * @param string
     */
    public static boolean isInteger(String string) {
        int len = string == null ? 0 : (string = string.trim()).length();
        if (len > 0) {
            int i = 0;

            char ch = string.charAt(i++);
            if (ch > 57 || ch < 48) {
                if (ch != 45 || len == i) {
                    return false;
                }
            }

            while (i < len) {
                ch = string.charAt(i++);
                if (ch > 57 || ch < 48) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 是否是一个小数（实数，包括正实数和负实数；包括八进制、十进制和十六进制）
     *
     * @param string
     */
    public static boolean isNumber(String string) {
        int length = string == null ? 0 : (string = string.trim()).length();
        if (length > 0) {
            boolean point = false;

            int i = 0;
            char ch = string.charAt(i++);
            if (ch == 46) {
                point = true;
            } else if (!((ch > 47 && ch < 58) || (ch > 64 && ch < 71) || (ch > 96 && ch < 103))) {
                if (ch != 45 || length == i) {
                    return false;
                }
            }

            ch = string.charAt(i++);
            if (ch == 48) {
                if (length > i) {
                    ch = string.charAt(i++);
                    if (ch == 88 || ch == 120) {
                        for (; i < length; i++) {
                            ch = string.charAt(i);
                            if ((ch > 47 && ch < 58) || (ch > 64 && ch < 71) || (ch > 96 && ch < 103)) {
                                continue;
                            } else {
                                if (ch != 46) {
                                    return false;
                                } else {
                                    if (point) {
                                        return false;
                                    } else {
                                        point = true;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                } else {
                    return true;
                }
            }
            for (; i < length; i++) {
                ch = string.charAt(i);
                if (ch > 57 || ch < 48) {
                    if (ch == 46) {
                        if (point) {
                            return false;
                        } else {
                            point = true;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 是否是一个小数（实数，包括正实数和负实数；只验证十进制数）
     *
     * @param string
     */
    public static boolean isDouble(String string) {
        int length = string == null ? 0 : (string = string.trim()).length();
        if (length > 0) {
            boolean point = false;

            char ch = string.charAt(0);
            int i = 0;
            if (ch == 45) {
                i = 1;
            } else if (ch == 46) {
                i = 1;
                point = true;
            }

            if (length > i) {
                for (; i < length; i++) {
                    ch = string.charAt(i);
                    if (ch > 57 || ch < 48) {
                        if (ch == 46) {
                            if (!point) {
                                point = true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}

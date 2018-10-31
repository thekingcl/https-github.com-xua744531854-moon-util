package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsHandler {
    /**
     * 计算
     *
     * @param left
     * @param right
     * @return
     */
    default Object handle( Object right, Object left) {
        throw new UnsupportedOperationException();
    }

    /**
     * 计算
     *
     * @param left
     * @param right
     * @return
     */
    Object handle(AsHandler right, AsHandler left, Object data);

    /**
     * 运算符优先级
     *
     * @return
     */
    default int getPriority() {
        return 99;
    }

    /**
     * 使用外部数据
     *
     * @param data
     * @return
     */
    default Object use(Object data) {
        throw new UnsupportedOperationException();
    }

    /**
     * 计算简单表达式
     *
     * @return
     */
    default Object use() {
        return use(null);
    }

    /*
     * --------------------------------------
     * 判断
     * --------------------------------------
     */

    /**
     * 计算器
     *
     * @return
     */
    default boolean isHandler() {
        return false;
    }

    /**
     * 取值器
     *
     * @return
     */
    default boolean isValuer() {
        return isConst() || isGetter();
    }

    /**
     * 普通常量
     *
     * @return
     */
    default boolean isConst() {
        return false;
    }

    /**
     * 是否使用外部数据
     *
     * @return
     */
    default boolean isGetter() {
        return false;
    }

    /**
     * 是否是一个方法执行
     *
     * @return
     */
    default boolean isInvoker() {
        return false;
    }
}

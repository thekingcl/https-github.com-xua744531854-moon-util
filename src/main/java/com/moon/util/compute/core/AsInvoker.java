package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsInvoker extends AsValuer {
    /**
     * 是否是一个方法执行
     *
     * @return
     */
    @Override
    default boolean isInvoker() {
        return true;
    }
}

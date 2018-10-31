package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsCompute extends AsHandler {
    /**
     * 计算器
     *
     * @return
     */
    @Override
    default boolean isHandler() {
        return true;
    }

    /**
     * 计算
     *
     * @param right
     * @param left
     * @param data
     * @return
     */
    @Override
    default Object handle(AsHandler right, AsHandler left, Object data) {
        return handle(right.use(data), left.use(data));
    }
}

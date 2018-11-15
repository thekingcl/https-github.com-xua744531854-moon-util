package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsCompute extends AsRunner {
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
    default Object handle(AsRunner right, AsRunner left, Object data) {
        return handle(right.run(data), left.run(data));
    }
}

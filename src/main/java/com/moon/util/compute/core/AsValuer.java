package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsValuer extends AsRunner {
    /**
     * 取值器
     *
     * @return
     */
    @Override
    default boolean isValuer() {
        return true;
    }

    /**
     * 计算
     *
     * @param left
     * @param right
     * @return
     */
    @Override
    default Object handle(AsRunner left, AsRunner right, Object data) {
        throw new UnsupportedOperationException();
    }
}

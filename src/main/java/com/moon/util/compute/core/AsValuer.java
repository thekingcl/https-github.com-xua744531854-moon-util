package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
interface AsValuer extends AsHandler {
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
    default Object handle(AsHandler left, AsHandler right, Object data) {
        throw new UnsupportedOperationException();
    }
}

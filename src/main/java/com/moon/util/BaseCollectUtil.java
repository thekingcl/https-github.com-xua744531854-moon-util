package com.moon.util;

import com.moon.enums.CollectEnum;

import java.util.Collection;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
class BaseCollectUtil {

    /*
     * ---------------------------------------------------------------------------------
     * adders
     * ---------------------------------------------------------------------------------
     */

    static <E, C extends Collection<E>> Collection concat0(C collect, C... cs) {
        Collection collection = CollectEnum.getAsSuperOrDeduce(collect).apply(collect);
        for (C c : cs) {
            collection.addAll(c);
        }
        return collection;
    }
}

package com.moon.office.excel.renderers;

/**
 * @author benshaoye
 */
interface VarSetter {
    default WorkCenterMap setVarAndRender(WorkCenterMap centerMap, Renderer target) {
        beforeSetAndRender(centerMap, target);
        if (target.isWhen(centerMap)) {
            target.beforeRender(centerMap);
            Renderer[] children = target.getChildren();
            for (int i = 0, len = children.length; i < len; i++) {
                children[i].render(centerMap);
            }
            target.afterRender(centerMap);
        }
        return centerMap;
    }

    default void beforeSetAndRender(WorkCenterMap centerMap, Renderer target) {
    }

    default boolean isIn() {
        return false;
    }

    default boolean isEq() {
        return false;
    }
}

package com.moon.office.excel.renderers;

/**
 * @author benshaoye
 */
interface Renderer {
    Renderer[] EMPTY = new Renderer[0];

    WorkCenterMap render(WorkCenterMap centerMap);

    default Renderer[] getChildren() {
        return EMPTY;
    }

    default WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        return centerMap;
    }

    default WorkCenterMap afterRender(WorkCenterMap centerMap) {
        return centerMap;
    }

    default boolean isWhen(WorkCenterMap centerMap) {
        return true;
    }
}

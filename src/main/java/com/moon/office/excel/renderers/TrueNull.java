package com.moon.office.excel.renderers;

/**
 * @author benshaoye
 */
enum TrueNull implements Renderer {
    NULL;

    private final Renderer[] children = new Renderer[0];

    @Override
    public WorkCenterMap render(WorkCenterMap centerMap) {
        return centerMap;
    }

    @Override
    public Renderer[] getChildren() {
        return children;
    }
}

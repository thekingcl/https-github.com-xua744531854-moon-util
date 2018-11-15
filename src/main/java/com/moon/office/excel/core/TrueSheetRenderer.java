package com.moon.office.excel.core;

import com.moon.util.compute.RunnerUtil;

/**
 * @author benshaoye
 */
final class TrueSheetRenderer extends AbstractRenderer<TableSheet> {
    private final String sheetName;

    protected TrueSheetRenderer(TableSheet annotation, CenterRenderer[] children, String[] formatted) {
        super(annotation, children, annotation.var(), formatted);
        this.sheetName = annotation.sheetName();
    }

    @Override
    public WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        return centerMap.createSheet(String.valueOf(isZero()
            ? RunnerUtil.run(sheetName, centerMap)
            : RunnerUtil.parseRun(sheetName, getDelimiters(), centerMap)
        ));
    }
}

package com.moon.office.excel.core;

import com.moon.util.compute.RunnerUtil;

/**
 * @author benshaoye
 */
final class WhenSheetRenderer extends AbstractRenderer<TableSheet> {
    private final String sheetName;
    private final String when;

    protected WhenSheetRenderer(TableSheet annotation, CenterRenderer[] children, String[] formatted) {
        super(annotation, children, annotation.var(), formatted);
        this.sheetName = annotation.sheetName();
        this.when = annotation.when().trim();
    }

    private Boolean whenValue;

    @Override
    protected void setWhen(Boolean value) {
        whenValue = value;
    }

    @Override
    public boolean isWhen(WorkCenterMap centerMap) {
        return getValue(centerMap, when, whenValue, whenSetter);
    }

    @Override
    public WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        return centerMap.createSheet(String.valueOf(isZero()
            ? RunnerUtil.run(sheetName, centerMap)
            : RunnerUtil.parseRun(sheetName, getDelimiters(), centerMap)
        ));
    }
}

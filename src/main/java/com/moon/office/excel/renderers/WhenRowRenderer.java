package com.moon.office.excel.renderers;

import com.moon.lang.StringUtil;
import com.moon.office.excel.annotations.TableRow;

/**
 * @author benshaoye
 */
final class WhenRowRenderer extends AbstractRenderer<TableRow> {
    private final String className;
    private final String skipRows;
    private final String when;

    protected WhenRowRenderer(TableRow annotation, CenterRenderer[] children, String[] formatted) {
        super(annotation, children, annotation.var(), formatted);
        this.skipRows = annotation.skipRows();
        this.when = annotation.when().trim();

        String className = annotation.className().trim();
        this.className = StringUtil.isBlank(className) ? null : className;
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
        centerMap.createNextRow(getSkipsValue(centerMap, skipRows));
        centerMap.setRowStyle(className);
        return centerMap;
    }
}

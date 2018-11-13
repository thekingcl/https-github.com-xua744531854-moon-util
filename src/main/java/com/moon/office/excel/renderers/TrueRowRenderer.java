package com.moon.office.excel.renderers;

import com.moon.lang.StringUtil;
import com.moon.office.excel.annotations.TableRow;

/**
 * @author benshaoye
 */
final class TrueRowRenderer extends AbstractRenderer<TableRow> {
    private final String className;
    private final String skipRows;

    protected TrueRowRenderer(TableRow annotation, Renderer[] children, String[] formatted) {
        super(annotation, children, annotation.var(), formatted);
        this.skipRows = annotation.skipRows();

        String className = annotation.className().trim();
        this.className = StringUtil.isBlank(className) ? null : className;
    }

    @Override
    public WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        centerMap.createNextRow(getSkipsValue(centerMap,skipRows));
        centerMap.setRowStyle(className);
        return centerMap;
    }
}

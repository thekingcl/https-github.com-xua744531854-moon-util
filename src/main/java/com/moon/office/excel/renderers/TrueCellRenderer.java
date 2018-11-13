package com.moon.office.excel.renderers;

import com.moon.lang.StringUtil;
import com.moon.office.excel.annotations.TableCell;
import com.moon.office.excel.enums.ValueType;
import com.moon.util.compute.RunnerUtil;

/**
 * @author benshaoye
 */
final class TrueCellRenderer extends BaseCellRenderer<TableCell> {
    private final String skipCells;
    private final String colspan;
    private final String rowspan;
    private final String value;
    private final ValueType valueType;
    private final String styleId;

    protected TrueCellRenderer(TableCell annotation, CenterRenderer[] children, String[] formatted) {
        super(annotation, children, annotation.var(), formatted);
        this.skipCells = annotation.skipCells();
        this.colspan = annotation.colspan();
        this.rowspan = annotation.rowspan();
        this.value = annotation.value();
        this.valueType = annotation.type();

        String styleId = annotation.className();
        this.styleId = StringUtil.isBlank(styleId) ? null : styleId;
    }

    protected int getRowspan(WorkCenterMap centerMap) {
        return getRowspanValue(centerMap, rowspan);
    }

    protected int getColspan(WorkCenterMap centerMap) {
        return getColspanValue(centerMap, colspan);
    }

    protected int getSkips(WorkCenterMap centerMap) {
        return getSkipsValue(centerMap, skipCells);
    }

    @Override
    public WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        centerMap.createNextCell(getColspan(centerMap), getRowspan(centerMap), getSkips(centerMap), valueType);
        centerMap.setCellStyleById(styleId);
        return centerMap;
    }

    @Override
    public WorkCenterMap afterRender(WorkCenterMap centerMap) {
        centerMap.setCellValue(getValue(centerMap));
        return centerMap;
    }

    protected Object getValue(WorkCenterMap centerMap) {
        Object data = isZero()
            ? RunnerUtil.run(value, centerMap)
            : RunnerUtil.parseRun(value, getDelimiters(), centerMap);
        return valueType.apply(data);
    }
}

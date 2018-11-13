package com.moon.office.excel.renderers;

import com.moon.util.function.IntBiConsumer;

import java.lang.annotation.Annotation;

/**
 * @author benshaoye
 */
abstract class BaseCellRenderer<T extends Annotation> extends AbstractRenderer<T> {
    protected BaseCellRenderer(T annotation, CenterRenderer[] children, String var, String[] delimiters) {
        super(annotation, children, var, delimiters);
    }

    protected int colspanValue = NOT_INIT;
    protected int rowspanValue = NOT_INIT;

    private final static IntBiConsumer<BaseCellRenderer> colspanValueSetter
        = (renderer, value) -> renderer.colspanValue = value;
    private final static IntBiConsumer<BaseCellRenderer> rowspanValueSetter
        = (renderer, value) -> renderer.rowspanValue = value;

    public int getColspanValue(WorkCenterMap centerMap, String expression) {
        return getValue(centerMap, expression, colspanValue, colspanValueSetter);
    }

    public int getRowspanValue(WorkCenterMap centerMap, String expression) {
        return getValue(centerMap, expression, rowspanValue, rowspanValueSetter);
    }
}

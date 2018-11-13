package com.moon.office.excel.renderers;

import com.moon.enums.ArraysEnum;
import com.moon.lang.StringUtil;
import com.moon.office.excel.annotations.Style;
import com.moon.office.excel.annotations.TableExcel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
class TrueExcelRenderer extends AbstractRenderer<TableExcel> {
    private final Map<String, CellStyleCreator> styleMaps;

    protected TrueExcelRenderer(TableExcel annotation, Renderer[] children) {
        super(annotation, children, annotation.var(), ArraysEnum.STRINGS.empty());

        Style[] styles = annotation.styles();
        if (styles.length > 0) {
            Map<String, CellStyleCreator> styleMaps = this.styleMaps = new HashMap<>();
            for (Style style : annotation.styles()) {
                styleMaps.put(StringUtil.requireNotBlank(style.className()), new CellStyleCreator(style));
            }
        } else {
            this.styleMaps = null;
        }
    }

    @Override
    public WorkCenterMap beforeRender(WorkCenterMap centerMap) {
        centerMap.setStyleMaps(styleMaps);
        return centerMap;
    }
}

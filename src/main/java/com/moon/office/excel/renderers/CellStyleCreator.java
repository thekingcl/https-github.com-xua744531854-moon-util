package com.moon.office.excel.renderers;

import com.moon.office.excel.annotations.Style;
import org.apache.poi.ss.usermodel.*;

/**
 * @author benshaoye
 */
class CellStyleCreator {

    private final Style style;
    private final short[] borderColors;
    private final BorderStyle[] borderStyles;
    private final int width;
    private final short height;

    private final short backgroundColor;
    private final FillPatternType patternType;

    public CellStyleCreator(Style style) {
        this.style = style;
        width = style.width();
        height = style.height();
        borderStyles = style.borderStyle();
        borderColors = style.borderColor();
        backgroundColor = style.backgroundColor();
        patternType = style.patternType();
    }

    public void setRowStyle(Workbook workbook, Row row) {
        CellStyle cellStyle = createCellStyle(workbook);
        row.setRowStyle(cellStyle);
        if (height > 0) {
            row.setHeight(height);
        }
    }

    public int setCellStyleAndGetWidth(Workbook workbook, Sheet sheet, Row row, Cell cell) {
        CellStyle cellStyle = createCellStyle(workbook);
        cell.setCellStyle(cellStyle);
        return width;
    }

    private CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        setBorderColor(cellStyle);
        setBorderStyle(cellStyle);
        setAlignment(cellStyle);
        setCellColor(cellStyle);
        return cellStyle;
    }

    private void setCellColor(CellStyle cellStyle) {
        cellStyle.setFillPattern(patternType);
        cellStyle.setFillForegroundColor(backgroundColor);
    }

    private void setAlignment(CellStyle cellStyle) {
        Style style = this.style;
        cellStyle.setAlignment(style.align());
        cellStyle.setVerticalAlignment(style.verticalAlign());
    }

    private void setBorderColor(CellStyle cellStyle) {
        short[] colors = this.borderColors;
        switch (colors.length) {
            case 0:
                break;
            case 1:
                cellStyle.setTopBorderColor(colors[0]);
                cellStyle.setRightBorderColor(colors[0]);
                cellStyle.setBottomBorderColor(colors[0]);
                cellStyle.setLeftBorderColor(colors[0]);
                break;
            case 2:
                cellStyle.setTopBorderColor(colors[0]);
                cellStyle.setBottomBorderColor(colors[0]);
                cellStyle.setRightBorderColor(colors[1]);
                cellStyle.setLeftBorderColor(colors[1]);
                break;
            case 3:
                cellStyle.setTopBorderColor(colors[0]);
                cellStyle.setRightBorderColor(colors[1]);
                cellStyle.setBottomBorderColor(colors[2]);
                cellStyle.setLeftBorderColor(colors[1]);
                break;
            default:
                cellStyle.setTopBorderColor(colors[0]);
                cellStyle.setRightBorderColor(colors[1]);
                cellStyle.setBottomBorderColor(colors[2]);
                cellStyle.setLeftBorderColor(colors[3]);
                break;
        }
    }

    private void setBorderStyle(CellStyle cellStyle) {
        BorderStyle[] borderStyles = this.borderStyles;
        switch (borderStyles.length) {
            case 0:
                break;
            case 1:
                cellStyle.setBorderTop(borderStyles[0]);
                cellStyle.setBorderRight(borderStyles[0]);
                cellStyle.setBorderBottom(borderStyles[0]);
                cellStyle.setBorderLeft(borderStyles[0]);
                break;
            case 2:
                cellStyle.setBorderTop(borderStyles[0]);
                cellStyle.setBorderBottom(borderStyles[0]);
                cellStyle.setBorderRight(borderStyles[1]);
                cellStyle.setBorderLeft(borderStyles[1]);
                break;
            case 3:
                cellStyle.setBorderTop(borderStyles[0]);
                cellStyle.setBorderRight(borderStyles[1]);
                cellStyle.setBorderBottom(borderStyles[2]);
                cellStyle.setBorderLeft(borderStyles[1]);
                break;
            default:
                cellStyle.setBorderTop(borderStyles[0]);
                cellStyle.setBorderRight(borderStyles[1]);
                cellStyle.setBorderBottom(borderStyles[2]);
                cellStyle.setBorderLeft(borderStyles[3]);
                break;
        }
    }
}

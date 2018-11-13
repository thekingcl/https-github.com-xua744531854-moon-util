package com.moon.office.excel.renderers;

import com.moon.office.excel.enums.ValueType;
import com.moon.util.compute.RunnerDataMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
class WorkCenterMap extends RunnerDataMap
    implements Supplier<Workbook> {
    private final static Class TEMP = WorkCenterMap.class;
    private final Workbook workbook;
    private final int lastRowIndex;
    private final HashMap<Integer, HashMap<Integer, Class>> mergeManage = new HashMap<>();

    public WorkCenterMap(Workbook workbook, Object... data) {
        super(data);
        this.workbook = workbook;
        this.lastRowIndex = (2 << (workbook instanceof HSSFWorkbook ? 16 : 20)) - 5;
    }

    public WorkCenterMap(Supplier<Workbook> type) {
        this(type.get());
    }

    @Override
    public Workbook get() {
        return workbook;
    }

    /*
     * -------------------------------------------------------
     * excel 创建相关
     * -------------------------------------------------------
     */

    private Map<String, CellStyleCreator> styleMaps;

    public void setStyleMaps(Map<String, CellStyleCreator> styleMaps) {
        this.styleMaps = styleMaps;
    }

    private Sheet currentSheet;
    private Row currentRow;
    private Cell currentCell;

    private int currentRowIndex;
    private int currentCellIndex;

    WorkCenterMap createSheet(String sheetName) {
        currentSheet = ensureCreateSheet(sheetName);
        currentRowIndex = 0;
        mergeManage.clear();
        return this;
    }

    private int prevRowIndex;

    WorkCenterMap createNextRow(int skips) {
        int actualIndex = prevRowIndex = currentRowIndex + skips, index;
        if (actualIndex > lastRowIndex) {
            createSheet(currentSheet.getSheetName());
            return createNextRow(0);
        } else {
            for (index = currentRowIndex - 1; index < actualIndex; index++) {
                mergeManage.remove(index);
            }

            currentRow = currentSheet.createRow(actualIndex);
            currentRowIndex = actualIndex + 1;

            currentCellIndex = 0;
            return this;
        }
    }

    private Sheet ensureCreateSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            return workbook.createSheet(sheetName);
        } else {
            String currentName = null;
            int index = sheetName.lastIndexOf('(');
            if (index > 0) {
                int endIndex = sheetName.indexOf(')', index);
                if (endIndex - index > 1) {
                    String wrapped = sheetName.substring(index + 1, endIndex);
                    try {
                        int prevNum = Integer.parseInt(wrapped);
                        currentName = sheetName.substring(0, index) + '(' + (prevNum + 1) + ')';
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
            return ensureCreateSheet(
                currentName == null
                    ? sheetName + "(1)"
                    : currentName
            );
        }
    }

    WorkCenterMap createNextCell(int colspan, int rowspan, int skips, ValueType type) {
        int index = currentCellIndex + skips;
        HashMap<Integer, Class> row = mergeManage.get(prevRowIndex);
        if (row != null) {
            for (; row.get(index) != null; index++) {
            }
        }
        Cell cell = this.currentCell = currentRow.createCell(index);
        cell.setCellType(type.TYPE);
        createMergeRegion(colspan, rowspan, index);
        return this;
    }

    private void createMergeRegion(int colspan, int rowspan, int index) {
        boolean test = (colspan > 1 && rowspan > 0) || (rowspan > 1 && colspan > 0);
        if (test) {
            int lastCell = index + colspan;
            int lastRow = currentRowIndex + rowspan;
            fillRegionCell(currentRowIndex - 1, lastRow - 2, index, lastCell - 1);
            currentCellIndex = lastCell;
        } else {
            currentCellIndex = index + 1;
        }
    }

    private void fillRegionCell(int firstRow, int lastRow, int firstCell, int lastCell) {
        currentSheet.addMergedRegion(new CellRangeAddress(
            firstRow, lastRow, firstCell, lastCell
        ));
        for (int i = firstRow, outerEnd = lastRow + 1; i < outerEnd; i++) {
            HashMap<Integer, Class> current = mergeManage.get(i);
            if (current == null) {
                mergeManage.put(i, current = new HashMap<>());
            }
            for (int j = firstCell, innerEnd = lastCell + 1; j < innerEnd; j++) {
                current.put(j, TEMP);
            }
        }
    }

    WorkCenterMap setCellValue(Object value) {
        currentCell.setCellValue(String.valueOf(value));
        return this;
    }

    public void setRowStyle(String className) {
        CellStyleCreator creator = getStyleCreator(className);
        if (creator != null) {
            creator.setRowStyle(workbook, currentRow);
        }
    }

    public void setCellStyleById(String className) {
        CellStyleCreator creator = getStyleCreator(className);
        if (creator != null) {
            int width = creator.setCellStyleAndGetWidth(workbook, currentSheet, currentRow, currentCell);
            if (width > 0) {
                currentSheet.setColumnWidth(currentCellIndex - 1, width);
            }
        }
    }

    public CellStyleCreator getStyleCreator(String className) {
        if (className != null && styleMaps != null) {
            CellStyleCreator creator = styleMaps.get(className);
            if (creator == null) {
                throw new IllegalArgumentException("Can not load style of className: " + className);
            }
            return creator;
        }
        return null;
    }
}

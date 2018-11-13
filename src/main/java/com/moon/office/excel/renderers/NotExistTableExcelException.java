package com.moon.office.excel.renderers;

/**
 * @author benshaoye
 */
class NotExistTableExcelException extends RuntimeException {
    public NotExistTableExcelException(String message) {
        super(message);
    }
}

package com.moon.office.excel.annotations;

/**
 * @author benshaoye
 */
public @interface ImportExcel {
    ImportSheet[] value() default {};

    Class target();
}

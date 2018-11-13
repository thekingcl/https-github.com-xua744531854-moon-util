package com.moon.office.excel.annotations;

/**
 * @author benshaoye
 */
public @interface ImportRow {
    ImportCell[] value() default {};
}

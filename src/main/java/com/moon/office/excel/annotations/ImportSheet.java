package com.moon.office.excel.annotations;

/**
 * @author benshaoye
 */
public @interface ImportSheet {
    ImportRow[] value() default {};
}

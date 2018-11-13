package com.moon.office.excel.renderers;

import com.moon.lang.ClassUtil;
import com.moon.lang.annotation.AnnotatedUtil;
import com.moon.lang.reflect.MethodUtil;
import com.moon.office.excel.ExcelUtil;
import com.moon.office.excel.annotations.TableExcel;
import com.moon.util.CollectionUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public class RendererUtil {
    protected RendererUtil() {
        noInstanceError();
    }

    private final static String NAME = ExcelUtil.class.getName();

    private final static Map<TableExcel, Renderer> CACHE = new HashMap<>();

    private final static TableExcel getAnnotation() {
        int foundCount = 0;
        String TARGET_NAME = NAME, className, methodName, foundName = null;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 2, len = elements.length; i < len; i++) {
            StackTraceElement element = elements[i];
            className = element.getClassName();
            if (foundCount > 0) {
                if (foundCount == 1) {
                    foundName = className;
                }
                if (!className.equals(foundName)) {
                    foundCount++;
                    continue;
                }
                methodName = element.getMethodName();
                if (methodName.matches("lambda\\$.*\\$\\d+")) {
                    foundCount++;
                    continue;
                }
                List<Method> methods = MethodUtil.getAllMethods(ClassUtil.forName(className), methodName);
                if (CollectionUtil.isNotEmpty(methods)) {
                    Method method = methods.get(0);
                    TableExcel excel = AnnotatedUtil.get(method, TableExcel.class);
                    if (excel == null) {
                        throw new NotExistTableExcelException(foundName + '.' + methodName);
                    }
                    return excel;
                }
                foundCount++;
            } else if (TARGET_NAME.equals(className)) {
                foundCount++;
            }
        }
        throw new NotExistTableExcelException(foundName);
    }

    private final static Renderer getOrParse(TableExcel excel) {
        Renderer renderer = CACHE.get(excel);
        if (renderer == null) {
            renderer = ParseUtil.parseExcel(excel);
            synchronized (CACHE) {
                if (CACHE.get(excel) == null) {
                    CACHE.put(excel, renderer);
                }
            }
        }
        return renderer;
    }

    protected final static Workbook parseAndRenderTo(Object... data) {
        return parseAndRenderTo(null, data);
    }

    protected final static Workbook parseAndRenderTo(Workbook workbook, Object... data) {
        TableExcel excel = getAnnotation();
        Renderer renderer = getOrParse(excel);
        WorkCenterMap centerMap = new WorkCenterMap(
            workbook == null ? excel.type().get() : workbook, data);
        return renderer.render(centerMap).get();
    }
}

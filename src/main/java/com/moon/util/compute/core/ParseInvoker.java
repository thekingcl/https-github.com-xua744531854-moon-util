package com.moon.util.compute.core;

import com.moon.lang.SupportUtil;
import com.moon.lang.ref.IntAccessor;
import com.moon.lang.reflect.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.reflect.FieldUtil.getAccessibleField;
import static com.moon.lang.reflect.MethodUtil.getPublicStaticMethods;
import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
final class ParseInvoker {
    private ParseInvoker() {
        noInstanceError();
    }

    final static Predicate<Method> tester = m -> m.getParameterCount() == 0;

    final static AsHandler parse(char[] chars, IntAccessor indexer, int len, String name, AsValuer prevValuer) {
        final int cache = indexer.get();
        final boolean isStaticCaller = prevValuer instanceof DataConstLoader;
        int curr = ParseUtil.skipWhitespace(chars, indexer, len);
        AsHandler result = null;
        if (curr == Constants.YUAN_LEFT) {
            curr = ParseUtil.skipWhitespace(chars, indexer, len);
            if (curr == Constants.YUAN_RIGHT) {
                // 无参
                if (isStaticCaller) {
                    // 静态方法
                    List<Method> methods = getPublicStaticMethods(
                        ((DataConstLoader) prevValuer).getValue(), name);
                    result = new EnsureInvokerEmpty(SupportUtil.matchOne(methods, tester));
                } else {
                    // 成员方法
                    result = new DataGetterLinker(prevValuer, new DataInvokeEmpty(name));
                }
            } else {
                // 支持一个参数
                AsHandler valuer = ParseCore.parse(chars, indexer.minus(), len, Constants.YUAN_RIGHT);
                if (isStaticCaller) {
                    // 静态方法
                    Class sourceType = ((DataConstLoader) prevValuer).getValue();
                    result = EnsureInvokerOne.of((AsValuer) valuer, sourceType, name);
                } else {
                    // 成员方法
                    result = new DataInvokeOne(prevValuer, (AsValuer) valuer, name);
                }
            }
        } else {
            if (isStaticCaller) {
                // 静态字段
                Class sourceType = ((DataConstLoader) prevValuer).getValue();
                Field field = requireNonNull(getAccessibleField(sourceType, name));
                result = DataConst.get(FieldUtil.getValue(field, sourceType));
            }
            indexer.set(cache);
        }
        return result;
    }
}

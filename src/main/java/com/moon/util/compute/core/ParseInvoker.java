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
import static com.moon.util.compute.core.Constants.YUAN_LEFT;
import static com.moon.util.compute.core.Constants.YUAN_RIGHT;
import static com.moon.util.compute.core.ParseUtil.skipWhitespaces;
import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
final class ParseInvoker {
    private ParseInvoker() {
        noInstanceError();
    }

    private final static Predicate<Method> NONE_PARAM = m -> m.getParameterCount() == 0;

    final static AsHandler parse(
        char[] chars, IntAccessor indexer, int len, String name, AsValuer prevValuer
    ) {
        final int cache = indexer.get();
        final boolean isStatic = prevValuer instanceof DataConstLoader;
        if (skipWhitespaces(chars, indexer, len) == YUAN_LEFT) {
            if (skipWhitespaces(chars, indexer, len) == YUAN_RIGHT) {
                // 无参方法调用
                return parseNoneParams(prevValuer, name, isStatic);
            } else {
                // 带有一个参数的方法调用
                return parseMoreParams(chars, indexer, len, prevValuer, name, isStatic);
            }
        } else {
            // 静态字段检测
            indexer.set(cache);
            return parseStaticField(prevValuer, name, isStatic);
        }
    }

    /*
     * 带有一个参数的方法调用
     */
    private final static AsHandler parseMoreParams(
        char[] chars, IntAccessor indexer, int len,
        AsValuer prev, String name, boolean isStatic
    ) {
        AsHandler valuer = ParseCore.parse(chars, indexer.minus(), len, YUAN_RIGHT);
        if (isStatic) {
            // 静态方法
            Class sourceType = ((DataConstLoader) prev).getValue();
            return EnsureInvokerOne.of((AsValuer) valuer, sourceType, name);
        } else {
            // 成员方法
            return new DataInvokeOne(prev, (AsValuer) valuer, name);
        }
    }

    /*
     * 无参方法调用
     */
    private final static AsHandler parseNoneParams(
        AsValuer prev, String name, boolean isStatic
    ) {
        if (isStatic) {
            // 静态方法
            List<Method> methods = getPublicStaticMethods(
                ((DataConstLoader) prev).getValue(), name);
            return new EnsureInvokerEmpty(SupportUtil.matchOne(methods, NONE_PARAM));
        } else {
            // 成员方法
            return new DataGetterLinker(prev, new DataInvokeEmpty(name));
        }
    }

    /*
     * 静态字段
     */
    private final static AsValuer parseStaticField(
        AsValuer prevValuer, String name, boolean isStatic
    ) {
        if (isStatic) {
            // 静态字段
            Class sourceType = ((DataConstLoader) prevValuer).getValue();
            Field field = requireNonNull(getAccessibleField(sourceType, name));
            return DataConst.get(FieldUtil.getValue(field, sourceType));
        }
        return null;
    }
}

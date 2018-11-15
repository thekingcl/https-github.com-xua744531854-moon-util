package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;
import com.moon.lang.reflect.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.moon.lang.SupportUtil.matchOne;
import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.lang.reflect.FieldUtil.getAccessibleField;
import static com.moon.lang.reflect.MethodUtil.getPublicStaticMethods;
import static com.moon.util.compute.core.Constants.*;
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

    final static AsRunner tryParseInvoker(
        char[] chars, IntAccessor indexer, int len, String name, AsValuer prevValuer
    ) {
        final int cache = indexer.get();
        final boolean isStatic = prevValuer instanceof DataConstLoader;
        if (skipWhitespaces(chars, indexer, len) == YUAN_LEFT) {
            if (skipWhitespaces(chars, indexer, len) == YUAN_RIGHT) {
                // 无参方法调用
                return parseNoneParams(prevValuer, name, isStatic);
            } else {
                // 带有参数的方法调用
                return parseHasParams(chars, indexer.minus(), len, prevValuer, name, isStatic);
            }
        } else {
            // 静态字段检测
            indexer.set(cache);
            return tryParseStaticField(prevValuer, name, isStatic);
        }
    }

    /**
     * 带有参数的方法调用
     */
    private final static AsRunner parseHasParams(
        char[] chars, IntAccessor indexer, int len,
        AsValuer prev, String name, boolean isStatic
    ) {
        for (List valuers = new ArrayList(); ; ) {
            valuers.add(ParseCore.parse(chars, indexer, len, YUAN_RIGHT, COMMA));
            if (chars[indexer.get() - 1] == YUAN_RIGHT) {
                return valuers.size() > 1
                    ? parseMultiParamCaller(valuers, prev, name, isStatic)
                    : parseOnlyParamCaller(valuers, prev, name, isStatic);
            }
        }
    }

    /**
     * 多参数调用的方法
     */
    private final static AsRunner parseMultiParamCaller(
        List<AsValuer> valuers, AsValuer prev, String name, boolean isStatic
    ) {
        if (isStatic) {

        } else {

        }
        throw new UnsupportedOperationException();
    }

    /**
     * 带有一个参数的方法
     */
    private final static AsRunner parseOnlyParamCaller(
        List<AsValuer> valuers, AsValuer prev, String name, boolean isStatic
    ) {
        if (isStatic) {
            // 静态方法
            Class sourceType = ((DataConstLoader) prev).getValue();
            return EnsureInvokerOne.of(valuers.get(0), sourceType, name);
        } else {
            // 成员方法
            return new DataInvokeOne(prev, valuers.get(0), name);
        }
    }

    /**
     * 无参方法调用
     */
    private final static AsRunner parseNoneParams(
        AsValuer prev, String name, boolean isStatic
    ) {
        if (isStatic) {
            // 静态方法
            return new EnsureInvokerEmpty(
                matchOne(getPublicStaticMethods(
                    ((DataConstLoader) prev).getValue(), name), NONE_PARAM));
        } else {
            // 成员方法
            return new DataGetterLinker(prev, new DataInvokeEmpty(name));
        }
    }

    /**
     * 尝试解析静态字段，如果不是静态字段调用返回 null
     */
    private final static AsValuer tryParseStaticField(
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

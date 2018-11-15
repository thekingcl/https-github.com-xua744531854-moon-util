package com.moon.util.compute.core;

import com.moon.lang.reflect.MethodUtil;

import java.lang.reflect.Method;

import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
final class EnsureInvokerEmpty  implements AsInvoker {
    final Method method;

    EnsureInvokerEmpty(Method method) {
        this.method = requireNonNull(method);
    }

    @Override
    public Object run(Object data) {
        return MethodUtil.invokeStatic(method);
    }
}
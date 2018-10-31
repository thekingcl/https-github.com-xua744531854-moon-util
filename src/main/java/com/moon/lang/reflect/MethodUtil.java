package com.moon.lang.reflect;

import com.moon.lang.ClassUtil;
import com.moon.lang.SupportUtil;
import com.moon.lang.ThrowUtil;
import com.moon.lang.ref.WeakCoordinate;
import com.moon.util.FilterUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author ZhangDongMin
 * @date 2018/1/30 9:39
 */
public final class MethodUtil {

    private final static WeakCoordinate<Class, Object, List<Method>>
        WEAK_ACCESS = WeakCoordinate.manageOne();
    private final static WeakCoordinate<Class, Object, List<Method>>
        WEAK_PUBLIC = WeakCoordinate.manageOne();
    private final static WeakCoordinate<Class, Object, List<Method>>
        WEAK_DECLARED = WeakCoordinate.manageOne();

    /*
     * method
     */

    public static Method getPublicMethod(Class clazz, String methodName) {
        List<Method> methodList = getPublicMethods(clazz, methodName);
        if (methodList.isEmpty()) {
            ThrowUtil.throwRuntime("Can not find public method: "
                + clazz + "." + methodName + "();");
        }
        return SupportUtil.matchOne(methodList, AssertModifier.empty);
    }

    public static Method getPublicMethod(Class clazz, String methodName, Class... parameterTypes) {
        List<Method> methodList = getPublicMethods(clazz, methodName, parameterTypes);
        if (methodList.isEmpty()) {
            ThrowUtil.throwRuntime("Can not find public method: "
                + clazz + "." + methodName + "(" + Arrays.toString(parameterTypes) + ");");
        }
        return methodList.get(0);
    }

    public static Method getDeclaredMethod(Class clazz, String methodName) {
        List<Method> methodList = getDeclaredMethods(clazz, methodName);
        if (methodList.isEmpty()) {
            ThrowUtil.throwRuntime("Can not find public method: "
                + clazz + "." + methodName + "();");
        }
        return SupportUtil.matchOne(methodList, AssertModifier.empty);
    }

    public static Method getDeclaredMethod(Class clazz, String methodName, Class... parameterTypes) {
        List<Method> methodList = getDeclaredMethods(clazz, methodName, parameterTypes);
        if (methodList.isEmpty()) {
            ThrowUtil.throwRuntime("Can not find public method: "
                + clazz + "." + methodName + "(" + Arrays.toString(parameterTypes) + ");");
        }
        return methodList.get(0);
    }

    /*
     * public methods: 所有方法，包含参数或不包含参数
     */

    public static List<Method> getPublicMethods(Class clazz) {
        return WEAK_DECLARED.get(Objects.requireNonNull(clazz),
            TypeEnum.PUBLIC, () -> UnmodifiableArrayList.unmodifiable(clazz.getMethods()));
    }

    public static List<Method> getPublicStaticMethods(Class clazz) {
        return WEAK_PUBLIC.get(clazz, TypeEnum.PUBLIC_STATIC, () -> {
            UnmodifiableArrayList<Method> STATIC = new UnmodifiableArrayList<>();
            FilterUtil.filter(getPublicMethods(clazz), AssertModifier.isStatic, STATIC);
            return STATIC.unmodifiable();
        });
    }

    public static List<Method> getPublicMemberMethods(Class clazz) {
        return WEAK_PUBLIC.get(clazz, TypeEnum.PUBLIC_MEMBER, () -> {
            UnmodifiableArrayList<Method> STATIC = new UnmodifiableArrayList<>();
            FilterUtil.filter(getPublicMethods(clazz), AssertModifier.isMember, STATIC);
            return STATIC.unmodifiable();
        });
    }

    // with name：

    public static List<Method> getPublicMethods(Class clazz, String methodName) {
        return WEAK_PUBLIC.get(clazz, methodName, () -> {
            UnmodifiableArrayList<Method> PUBLIC = new UnmodifiableArrayList<>();
            FilterUtil.filter(getPublicMethods(clazz), nameMatcher(methodName), PUBLIC);
            return PUBLIC.unmodifiable();
        });
    }

    public static List<Method> getPublicStaticMethods(Class clazz, String methodName) {
        UnmodifiableArrayList<Method> STATIC = new UnmodifiableArrayList<>();
        FilterUtil.filter(getPublicMethods(clazz, methodName), AssertModifier.isStatic, STATIC);
        return STATIC.unmodifiable();
    }

    public static List<Method> getPublicMemberMethods(Class clazz, String methodName) {
        UnmodifiableArrayList<Method> STATIC = new UnmodifiableArrayList<>();
        FilterUtil.filter(getPublicMethods(clazz, methodName), AssertModifier.isMember, STATIC);
        return STATIC.unmodifiable();
    }

    // name and parameters

    /**
     * 找出名字为 methodName，参数类型列表最符合 parameterTypes 的方法，
     * 如果存在此方法，那么按 parameterTypes 类型传参能被正确执行，
     * 结果返回一个集合，前面的是首选执行方法，
     * 越前面匹配程度越高，越后面兼容性越高
     * 不存在相应方法时返回空集合
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static List<Method> getPublicMethods(Class clazz, String methodName, Class... parameterTypes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getPublicMethodByParameterTypes(clazz, parameterTypes), nameMatcher(methodName), methods);
        return methods.unmodifiable();
    }

    public static List<Method> getPublicStaticMethods(Class clazz, String methodName, Class... classes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getPublicMethods(clazz, methodName, classes), AssertModifier.isStatic, methods);
        return methods.unmodifiable();
    }

    public static List<Method> getPublicMemberMethods(Class clazz, String methodName, Class... classes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getPublicMethods(clazz, methodName, classes), AssertModifier.isMember, methods);
        return methods.unmodifiable();
    }

    /*
     * declared methods
     */

    public static List<Method> getDeclaredMethods(Class clazz) {
        return WEAK_DECLARED.get(Objects.requireNonNull(clazz), TypeEnum.DECLARED,
            () -> new UnmodifiableArrayList(clazz.getDeclaredMethods()).unmodifiable());
    }

    public static List<Method> getDeclaredStaticMethods(Class clazz) {
        return WEAK_DECLARED.get(clazz, TypeEnum.DECLARED_STATIC, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getDeclaredMethods(clazz), AssertModifier.isStatic, methods);
            return methods.unmodifiable();
        });
    }

    public static List<Method> getDeclaredMemberMethods(Class clazz) {
        return WEAK_DECLARED.get(clazz, TypeEnum.DECLARED_MEMBER, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getDeclaredMethods(clazz), AssertModifier.isMember, methods);
            return methods.unmodifiable();
        });
    }

    // name

    public static List<Method> getDeclaredMethods(Class clazz, String methodName) {
        return WEAK_DECLARED.get(clazz, Objects.requireNonNull(methodName), () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getDeclaredMethods(clazz), nameMatcher(methodName), methods);
            return methods.unmodifiable();
        });
    }

    public static List<Method> getDeclaredStaticMethods(Class clazz, String methodName) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getDeclaredMethods(clazz, methodName), AssertModifier.isStatic, methods);
        return methods.unmodifiable();
    }

    public static List<Method> getDeclaredMemberMethods(Class clazz, String methodName) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getDeclaredMethods(clazz, methodName), AssertModifier.isMember, methods);
        return methods.unmodifiable();
    }

    // name and parameters

    public static List<Method> getDeclaredMethods(Class clazz, String methodName, Class... parameterTypes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        FilterUtil.filter(getDeclaredMethodByParameterTypes(clazz, parameterTypes), nameMatcher(methodName), methods);
        return methods.unmodifiable();
    }

    public static List<Method> getDeclaredStaticMethods(Class clazz, String methodName, Class... parameterTypes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        ReflectionSupport.filter(getDeclaredMethodByParameterTypes(clazz, parameterTypes), methods,
            nameMatcher(methodName), AssertModifier.isStatic);
        return methods.unmodifiable();
    }

    public static List<Method> getDeclaredMemberMethods(Class clazz, String methodName, Class... parameterTypes) {
        UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
        ReflectionSupport.filter(getDeclaredMethodByParameterTypes(clazz, parameterTypes), methods,
            nameMatcher(methodName), AssertModifier.isMember);
        return methods.unmodifiable();
    }

    /*
     * all methods
     */

    public static List<Method> getAllDeclaredMethods(Class clazz) {
        return WEAK_DECLARED.get(clazz, TypeEnum.ALL, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            Class currCls = clazz, current;
            for (; ; currCls = currCls.getSuperclass()) {
                if ((current = currCls) == null) {
                    return methods.unmodifiable();
                } else {
                    methods.addAll(getDeclaredMethods(current));
                }
            }
        });
    }

    public static List<Method> getAllDeclaredStaticMethods(Class clazz) {
        return WEAK_DECLARED.get(clazz, TypeEnum.ALL_STATIC, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getAllDeclaredMethods(clazz), AssertModifier.isStatic, methods);
            return methods.unmodifiable();
        });
    }

    public static List<Method> getAllDeclaredMemberMethods(Class clazz) {
        return WEAK_DECLARED.get(clazz, TypeEnum.ALL_MEMBER, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getAllDeclaredMethods(clazz), AssertModifier.isMember, methods);
            return methods.unmodifiable();
        });
    }

    public static List<Method> getAccessibleMemberMethods(Class clazz, String name) {
        return WEAK_ACCESS.get(clazz, TypeEnum.ACCESSIBLE, () -> {
            UnmodifiableArrayList<Method> methods = new UnmodifiableArrayList<>();
            FilterUtil.filter(getAllDeclaredMemberMethods(clazz), nameMatcher(name), methods);
            Collections.sort(methods, Comparator.comparingInt(Method::getParameterCount));
            return methods.unmodifiable();
        });
    }

    /*
     * invokers
     */

    public static Object invoke(String methodName, Object source, Object... arguments) {
        return invoke(false, methodName, source, arguments);
    }

    public static Object invoke(boolean accessAble, String methodName, Object source, Object... arguments) {
        Class clazz = source.getClass();
        if (arguments.length > 0) {
            Class[] classes = ClassUtil.getClasses(arguments);
            Method method = getDeclaredMethod(clazz, methodName, classes);
            return invoke(accessAble, method, source, arguments);
        } else {
            Method method = getDeclaredMethod(clazz, methodName);
            return invoke(accessAble, method, source);
        }
    }

    public static Object invoke(Method method, Object source, Object... arguments) {
        return invoke(false, method, source, arguments);
    }

    public static Object invoke(boolean accessAble, Method method, Object source, Object... arguments) {
        try {
            Object ret;
            if (accessAble && !ModifierUtil.isAccessible(method)) {
                method.setAccessible(true);
                ret = method.invoke(source, arguments);
                method.setAccessible(false);
            } else {
                ret = method.invoke(source, arguments);
            }
            return ret;
        } catch (Exception e) {
            return ThrowUtil.throwRuntime(e);
        }
    }

    public static Object invokeStatic(String methodName, Class clazz, Object... arguments) {
        return invokeStatic(false, methodName, clazz, arguments);
    }

    public static Object invokeStatic(boolean accessAble, String methodName, Class clazz, Object... arguments) {
        List<Method> methods = getDeclaredMethods(clazz, methodName, ClassUtil.getClasses(arguments));
        Method method = methods.get(0);
        return invokeStatic(accessAble, method, arguments);
    }

    public static Object invokeStatic(Method method, Object... arguments) {
        return invokeStatic(false, method, arguments);
    }

    public static Object invokeStatic(boolean accessAble, Method method, Object... arguments) {
        return invoke(accessAble, method, null, arguments);
    }

    /*
     * privates
     */

    private static List<Method> getDeclaredMethodByParameterTypes(Class clazz, Class... parameterTypes) {
        return WEAK_DECLARED.get(clazz, Arrays.hashCode(parameterTypes), () -> {
            List<Method> matchTypes = getDeclaredMethods(clazz);
            return ReflectionSupport.findByParameterTypes(matchTypes, matchTypes.size(), parameterTypes);
        });
    }

    private static List<Method> getPublicMethodByParameterTypes(Class clazz, Class... parameterTypes) {
        return WEAK_PUBLIC.get(clazz, Arrays.hashCode(parameterTypes), () -> {
            List<Method> matchTypes = getPublicMethods(clazz);
            return ReflectionSupport.findByParameterTypes(matchTypes, matchTypes.size(), parameterTypes);
        });
    }

    /*
     * assertions
     */

    private static Predicate<Method> nameMatcher(String methodName) {
        final String name = methodName.trim();
        return method -> method.getName().equals(name);
    }

    enum AssertModifier implements Predicate<Method> {
        isStatic {
            @Override
            public boolean test(Method method) {
                return Modifier.isStatic(method.getModifiers());
            }
        },
        isMember {
            @Override
            public boolean test(Method method) {
                return !Modifier.isStatic(method.getModifiers());
            }
        },

        empty {
            @Override
            public boolean test(Method method) {
                return method.getParameterCount() == 0;
            }
        }
    }
}

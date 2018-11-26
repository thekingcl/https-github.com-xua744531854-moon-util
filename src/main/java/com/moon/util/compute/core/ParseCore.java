package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;
import com.moon.lang.ref.ReferenceUtil;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.IntPredicate;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * 可能出现的位置：
 * 普通表达式、Map 或 List 的项、方法参数、方法执行对象
 *
 * @author benshaoye
 */
class ParseCore {
    private ParseCore() {
        noInstanceError();
    }

    private final static Map<String, AsRunner> CACHE = ReferenceUtil.manageMap();

    private static final synchronized AsRunner putCache(String expression, AsRunner runner) {
        if (CACHE.get(expression) == null) {
            CACHE.put(expression, runner);
        }
        return runner;
    }

    final static AsRunner parse(String expression) {
        AsRunner runner = CACHE.get(expression);
        if (runner == null) {
            if (expression == null) {
                return DataConst.NULL;
            }
            char[] chars = expression.trim().toCharArray();
            runner = putCache(expression, parse(chars, IntAccessor.of(), chars.length));
        }
        return runner;
    }

    final static AsRunner parse(
        char[] chars, IntAccessor indexer, int len
    ) {
        return parse(chars, indexer, len, -1);
    }

    final static AsRunner parse(
        char[] chars, IntAccessor indexer, int len, int end
    ) {
        return parse(chars, indexer, len, end, -1);
    }

    final static AsRunner parse(
        char[] chars, IntAccessor indexer, int len, int end0, int end1
    ) {
        return parse(chars, indexer, len, end0, end1, IntTesters.FALSE);
    }

    final static AsRunner parse(
        char[] chars, IntAccessor indexer, int len, int end0, int end1, IntPredicate tester
    ) {
        AsRunner handler = null;
        DataGetterThree.Builder builder = null;
        LinkedList<AsRunner> values = new LinkedList<>(), methods = new LinkedList();
        for (int curr; indexer.get() < len; ) {
            curr = ParseUtil.skipWhitespaces(chars, indexer, len);
            if (curr == end0 || curr == end1 || tester.test(curr)) {
                if (curr == YUAN_RIGHT) {
                    cleanMethodsTo(values, methods, DataComputes.YUAN_LEFT);
                }
                break;
            } else if (curr == ASK) {
                // ?
                builder = new DataGetterThree.Builder(toRunner(values, methods));
                values = new LinkedList<>();
                methods = new LinkedList();
            } else if (curr == COLON) {
                // :
                builder.setTrueRunner(toRunner(values, methods));
                values = new LinkedList<>();
                methods = new LinkedList();
            } else {
                handler = core(chars, indexer, len, curr, values, methods, handler);
            }
        }
        return builder == null ? toRunner(values, methods) : builder.setFalseRunner(toRunner(values, methods)).build();
    }

    private final static AsRunner toRunner(LinkedList<AsRunner> values, LinkedList<AsRunner> methods) {
        return DataGetterCalculator.valueOf(cleanMethodsTo(values, methods, null));
    }

    private final static AsRunner core(
        char[] chars, IntAccessor indexer, int len, int curr,
        LinkedList<AsRunner> values, LinkedList<AsRunner> methods, AsRunner prevHandler
    ) {
        AsRunner handler;
        if (ParseUtil.isStr(curr)) {
            values.add(handler = ParseConst.parseStr(chars, indexer, curr));
        } else if (ParseUtil.isNum(curr)) {
            values.add(handler = ParseConst.parseNum(chars, indexer, len, curr));
        } else if (ParseUtil.isVar(curr)) {
            values.add(handler = ParseGetter.parseVar(chars, indexer, len, curr));
        } else {
            switch (curr) {
                case PLUS:
                    // +
                    handler = compareAndSwapSymbol(values, methods, DataComputes.PLUS);
                    break;
                case MINUS:
                    // -
                    if (prevHandler == null || prevHandler.isHandler()) {
                        values.add(handler = ParseOpposite.parse(chars, indexer, len));
                    } else {
                        handler = compareAndSwapSymbol(values, methods, DataComputes.MINUS);
                    }
                    break;
                case MULTI:
                    // *
                    handler = compareAndSwapSymbol(values, methods, DataComputes.MULTI);
                    break;
                case DIVIDE:
                    // /
                    handler = compareAndSwapSymbol(values, methods, DataComputes.DIVIDE);
                    break;
                case MOD:
                    // %
                    handler = compareAndSwapSymbol(values, methods, DataComputes.MOD);
                    break;
                case NOT_OR:
                    // ^
                    handler = compareAndSwapSymbol(values, methods, DataComputes.NOT_OR);
                    break;
                case EQ:
                    // ==
                    ParseUtil.assertTrue(chars[indexer.get()] == EQ, chars, indexer);
                    handler = compareAndSwapSymbol(values, methods, DataComputes.EQ);
                    break;
                case GT:
                    // >、>=
                    if (chars[indexer.get()] == GT) {
                        handler = compareAndSwapSymbol(values, methods, DataComputes.BIT_RIGHT);
                    } else {
                        handler = toGtLtAndOr(chars, indexer, values, methods,
                            EQ, DataComputes.GT_OR_EQ, DataComputes.GT);
                    }
                    break;
                case LT:
                    // <、<=
                    if (chars[indexer.get()] == LT) {
                        handler = compareAndSwapSymbol(values, methods, DataComputes.BIT_LEFT);
                    } else {
                        handler = toGtLtAndOr(chars, indexer, values, methods,
                            EQ, DataComputes.LT_OR_EQ, DataComputes.LT);
                    }
                    break;
                case AND:
                    // && 、&
                    handler = toGtLtAndOr(chars, indexer, values, methods,
                        AND, DataComputes.AND, DataComputes.BIT_AND);
                    break;
                case OR:
                    // || 、|
                    handler = toGtLtAndOr(chars, indexer, values, methods,
                        OR, DataComputes.OR, DataComputes.BIT_OR);
                    break;
                case NOT:
                    // !
                    values.add(handler = ParseGetter.parseNot(chars, indexer, len));
                    break;
                case CALLER:
                    // @
                    values.add(handler = ParseGetter.parseCaller(chars, indexer, len));
                    break;
                case DOT:
                    // .
                    values.add(handler = ParseGetter.parseDot(chars, indexer, len, values.pollLast()));
                    break;
                case HUA_LEFT:
                    // {
                    values.add(handler = ParseCurly.parse(chars, indexer, len));
                    break;
                case FANG_LEFT:
                    // [
                    handler = ParseGetter.parseFang(chars, indexer, len);
                    if (prevHandler != null && prevHandler.isValuer()) {
                        ParseUtil.assertTrue(handler.isValuer(), chars, indexer);
                        handler = ((DataGetterFang) handler).toComplex(prevHandler);
                        values.pollLast();
                    }
                    values.add(handler);
                    break;
                case YUAN_LEFT:
                    // (
                    values.add(handler = ParseGetter.parseYuan(chars, indexer, len));
                    break;
                default:
                    // error
                    handler = ParseUtil.throwErr(chars, indexer);
                    break;
            }
        }
        return handler;
    }

    private final static AsCompute toGtLtAndOr(
        char[] chars, IntAccessor indexer,
        LinkedList<AsRunner> values, LinkedList<AsRunner> methods,
        int testTarget, DataComputes matchType, DataComputes defaultType
    ) {
        DataComputes type;
        if (chars[indexer.get()] == testTarget) {
            indexer.add();
            type = matchType;
        } else {
            type = defaultType;
        }
        return compareAndSwapSymbol(values, methods, type);
    }

    private final static AsCompute compareAndSwapSymbol(
        LinkedList<AsRunner> values, LinkedList<AsRunner> methods, AsCompute computer
    ) {
        AsRunner prev = methods.peekFirst();
        int currPriority = computer.getPriority();
        if (isBoundary(prev) || currPriority > prev.getPriority()) {
            methods.offerFirst(computer);
        } else {
            while (isNotBoundary(prev = methods.pollFirst())
                && prev.getPriority() >= currPriority) {
                values.add(prev);
            }
            if (prev != null) {
                methods.offerFirst(prev);
            }
            methods.offerFirst(computer);
        }
        return computer;
    }


    private final static LinkedList<AsRunner> cleanMethodsTo(
        LinkedList<AsRunner> values, LinkedList<AsRunner> methods, Object end
    ) {
        AsRunner computer;
        while ((computer = methods.pollFirst()) != end && computer != null) {
            values.add(computer);
        }
        return values;
    }

    private final static boolean isBoundary(AsRunner computer) {
        return computer == null || computer == DataComputes.YUAN_LEFT;
    }

    private final static boolean isNotBoundary(AsRunner computer) {
        return !isBoundary(computer);
    }
}

package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.IntPredicate;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * 负数还未支持，
 * 可能出现的位置：
 * 普通表达式、Map 或 List 的项、方法参数、方法执行对象
 *
 * @author benshaoye
 */
class ParseCore {
    private ParseCore() {
        noInstanceError();
    }

    enum Testers implements IntPredicate {
        FALSE {
            @Override
            public boolean test(int value) {
                return false;
            }
        },
        NOT_NUM {
            @Override
            public boolean test(int value) {
                return !ParseUtil.isNum(value);
            }
        }
    }

    private final static Map<String, AsHandler> CACHE = new HashMap<>();

    final static AsHandler parse(String expression) {
        AsHandler handler = CACHE.get(expression);
        if (handler == null) {
            if (expression == null) {
                handler = DataConstNull.NULL;
            } else {
                char[] chars = expression.trim().toCharArray();
                IntAccessor indexer = IntAccessor.of();
                final int len = chars.length;
                handler = parse(chars, indexer, len);
            }
            synchronized (CACHE) {
                if (CACHE.get(expression) == null) {
                    CACHE.put(expression, handler);
                }
            }
        }
        return handler;
    }

    final static AsHandler parse(
        char[] chars, IntAccessor indexer, int len
    ) {
        return parse(chars, indexer, len, -1);
    }

    final static AsHandler parse(
        char[] chars, IntAccessor indexer, int len, int end
    ) {
        return parse(chars, indexer, len, end, -1);
    }

    final static AsHandler parse(
        char[] chars, IntAccessor indexer, int len, int end0, int end1
    ) {
        return parse(chars, indexer, len, end0, end1, Testers.FALSE);
    }

    final static AsHandler parse(
        char[] chars, IntAccessor indexer, int len, int end0, int end1, IntPredicate tester
    ) {
        LinkedList<AsHandler> values = new LinkedList<>();
        Deque<AsHandler> methods = new LinkedList();
        AsHandler handler = null;
        int curr;
        for (; indexer.get() < len; ) {
            curr = ParseUtil.skipWhitespace(chars, indexer, len);
            if (curr == end0 || curr == end1 || tester.test(curr)) {
                if (curr == YUAN_RIGHT) {
                    cleanMethodsTo(values, methods, Computes.YUAN_LEFT);
                }
                break;
            }
            handler = core(chars, indexer, len, curr, values, methods, handler);
        }
        return Calculator.valueOf(cleanMethodsTo(values, methods, null));
    }

    private final static AsHandler core(
        char[] chars, IntAccessor indexer, int len, int curr,
        LinkedList<AsHandler> values, Deque<AsHandler> methods, AsHandler prevHandler
    ) {
        AsHandler valuer, handler;
        if (ParseUtil.isStr(curr)) {
            values.add(handler = ParseConst.parseStr(chars, indexer, curr));
        } else if (ParseUtil.isNum(curr)) {
            values.add(handler = ParseConst.parseNum(chars, indexer, len, curr));
        } else if (ParseUtil.isVar(curr)) {
            values.add(handler = ParseGetter.parseVar(chars, indexer, len, curr));
        } else {
            Computes type;
            switch (curr) {
                case PLUS:
                    // +
                    handler = compareAndSwapSymbol(values, methods, Computes.PLUS);
                    break;
                case MINUS:
                    // -
                    if (prevHandler == null || prevHandler.isHandler()) {
                        values.add(handler = ParseOpposite.parse(chars, indexer, len));
                    } else {
                        handler = compareAndSwapSymbol(values, methods, Computes.MINUS);
                    }
                    break;
                case MULTI:
                    // *
                    handler = compareAndSwapSymbol(values, methods, Computes.MULTI);
                    break;
                case DIVIDE:
                    // /
                    handler = compareAndSwapSymbol(values, methods, Computes.DIVIDE);
                    break;
                case MOD:
                    // %
                    handler = compareAndSwapSymbol(values, methods, Computes.MOD);
                    break;
                case NOT_OR:
                    // ^
                    handler = compareAndSwapSymbol(values, methods, Computes.NOT_OR);
                    break;
                case EQ:
                    // ==
                    ParseUtil.assertTrue(chars[indexer.get()] == EQ, chars, indexer);
                    handler = compareAndSwapSymbol(values, methods, Computes.EQ);
                    break;
                case GT:
                    // >、>=
                    if (chars[indexer.get()] == EQ) {
                        indexer.add();
                        type = Computes.GT_OR_EQ;
                    } else {
                        type = Computes.GT;
                    }
                    handler = compareAndSwapSymbol(values, methods, type);
                    break;
                case LT:
                    // <、<=
                    if (chars[indexer.get()] == EQ) {
                        indexer.add();
                        type = Computes.LT_OR_EQ;
                    } else {
                        type = Computes.LT;
                    }
                    handler = compareAndSwapSymbol(values, methods, type);
                    break;
                case AND:
                    // && 、&
                    if (chars[indexer.get()] == AND) {
                        indexer.add();
                        type = Computes.AND;
                    } else {
                        type = Computes.BIT_AND;
                    }
                    handler = compareAndSwapSymbol(values, methods, type);
                    break;
                case OR:
                    // || 、|
                    if (chars[indexer.get()] == OR) {
                        indexer.add();
                        type = Computes.OR;
                    } else {
                        type = Computes.BIT_OR;
                    }
                    handler = compareAndSwapSymbol(values, methods, type);
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
                    AsValuer prevValuer = (AsValuer) values.pollLast();
                    valuer = ParseGetter.parseDot(chars, indexer, len);
                    ParseUtil.assertTrue(valuer.isValuer(), chars, indexer);
                    AsHandler invoker = ParseInvoker.parse(chars, indexer, len, valuer.toString(), prevValuer);
                    valuer = invoker == null ? new DataGetterLinker(prevValuer, (AsValuer) valuer) : invoker;
                    values.add(valuer);
                    handler = valuer;
                    break;
                case HUA_LEFT:
                    // {
                    values.add(handler = ParseCurly.parse(chars, indexer, len));
                    break;
                case FANG_LEFT:
                    // [
                    valuer = ParseGetter.parseFang(chars, indexer, len);
                    if (prevHandler != null && prevHandler.isValuer()) {
                        ParseUtil.assertTrue(valuer.isValuer(), chars, indexer);
                        valuer = ((DataGetterFang) valuer).toComplex((AsValuer) prevHandler);
                        values.pollLast();
                    }
                    values.add(valuer);
                    handler = valuer;
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

    private final static AsCompute compareAndSwapSymbol(
        LinkedList<AsHandler> values, Deque<AsHandler> methods, AsCompute computer
    ) {
        AsHandler prev = methods.peekFirst();
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


    private final static LinkedList<AsHandler> cleanMethodsTo(
        LinkedList<AsHandler> values, Deque<AsHandler> methods, Object end
    ) {
        AsHandler computer;
        while ((computer = methods.pollFirst()) != end && computer != null) {
            values.add(computer);
        }
        return values;
    }

    private final static boolean isBoundary(AsHandler computer) {
        return computer == null || computer == Computes.YUAN_LEFT;
    }

    private final static boolean isNotBoundary(AsHandler computer) {
        return !isBoundary(computer);
    }
}

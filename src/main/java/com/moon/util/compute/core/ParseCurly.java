package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * @author benshaoye
 */
final class ParseCurly {
    private ParseCurly() {
        noInstanceError();
    }

    final static AsRunner parse(char[] chars, IntAccessor indexer, int len) {
        int curr = ParseUtil.skipWhitespaces(chars, indexer, len);
        AsRunner handler = tryEmpty(chars, indexer, len, curr);
        if (handler == null) {
            LinkedList<BiConsumer> creators = new LinkedList<>();
            CreateType type = getType(chars, indexer, len, curr);
            handler = type == CreateType.LIST
                ? parseList(chars, indexer, len, curr, creators)
                : parseMap(chars, indexer, len, curr, creators);
        }
        return handler;
    }

    private final static AsRunner parseList(
        char[] chars, IntAccessor indexer, int len, int curr,
        final LinkedList<BiConsumer> creators
    ) {
        AsRunner valuer;
        outer:
        for (int next = curr; ; ) {
            inner:
            switch (next) {
                case HUA_RIGHT:
                    return createAsGetter(creators, CreateType.LIST);
                case COMMA:
                    valuer = DataConst.NULL;
                    break inner;
                case SINGLE_QUOTE:
                case DOUBLE_QUOTE:
                    valuer = ParseConst.parseStr(chars, indexer, next);
                    break inner;
                default:
                    valuer = ParseCore.parse(chars, indexer.minus(), len, COMMA, HUA_RIGHT);
                    if ((next = chars[indexer.get() - 1]) == HUA_RIGHT) {
                        creators.add(new ListAdder(valuer));
                        continue outer;
                    }
                    break inner;
            }
            creators.add(new ListAdder(valuer));
            next = ParseUtil.skipWhitespaces(chars, indexer, len);
            if (next == COMMA) {
                next = ParseUtil.skipWhitespaces(chars, indexer, len);
            }
        }
    }

    private static class ListAdder implements BiConsumer<List, Object> {

        final AsRunner valuer;

        private ListAdder(AsRunner valuer) {
            this.valuer = valuer == null ? DataConst.NULL : valuer;
        }

        @Override
        public void accept(List list, Object data) {
            list.add(valuer.run(data));
        }
    }

    private final static AsRunner parseMap(
        char[] chars, IntAccessor indexer, int len, int curr,
        final LinkedList<BiConsumer> creators
    ) {
        AsRunner key;
        for (int next = curr, index; ; ) {
            switch (next) {
                case HUA_RIGHT:
                    return createAsGetter(creators, CreateType.MAP);
                case SINGLE_QUOTE:
                case DOUBLE_QUOTE:
                    key = ParseConst.parseStr(chars, indexer, next);
                    break;
                default:
                    if (ParseUtil.isNum(next)) {
                        key = ParseConst.parseNum(chars, indexer, len, next);
                    } else if (ParseUtil.isVar(next)) {
                        key = ParseGetter.parseVar(chars, indexer, len, next);
                        if (key.isGetter()) {
                            key = DataConst.get(key.toString());
                        }
                    } else {
                        key = ParseUtil.throwErr(chars, indexer);
                    }
                    break;
            }
            ParseUtil.assertTrue(
                ParseUtil.skipWhitespaces(
                    chars, indexer, len
                ) == COLON, chars, indexer
            );

            index = indexer.get();
            next = ParseUtil.skipWhitespaces(chars, indexer, len);
            ParseUtil.assertTrue(
                next != COMMA && next != HUA_RIGHT, chars, indexer
            );
            indexer.set(index);

            creators.add(new MapPutter(
                (AsConst) key,
                ParseCore.parse(
                    chars, indexer, len, COMMA, HUA_RIGHT
                )
            ));
            if ((next = chars[indexer.get() - 1]) == COMMA) {
                next = ParseUtil.skipWhitespaces(chars, indexer, len);
            }
        }
    }

    private static class MapPutter implements BiConsumer<Map, Object> {
        final AsConst key;
        final AsRunner valuer;

        private MapPutter(AsConst key, AsRunner valuer) {
            this.key = key;
            this.valuer = valuer;
        }

        @Override
        public void accept(Map map, Object data) {
            map.put(key.run(), valuer.run(data));
        }
    }

    private static AsGetter createAsGetter(LinkedList<BiConsumer> creators, CreateType type) {
        return creators.isEmpty() ? type
            : new DataGetterCurly(
            creators.toArray(
                new BiConsumer[creators.size()]
            ), type);
    }

    private final static CreateType getType(char[] chars, IntAccessor indexer, int len, int curr) {
        final int index = indexer.get();
        CreateType type;
        switch (curr) {
            case COMMA:
            case HUA_LEFT:
            case FANG_LEFT:
            case YUAN_LEFT:
                type = CreateType.LIST;
                break;
            case SINGLE_QUOTE:
            case DOUBLE_QUOTE:
                ParseConst.parseStr(chars, indexer, curr);
                type = doNext(chars, indexer, curr);
                break;
            default:
                if (ParseUtil.isNum(curr)) {
                    ParseConst.parseNum(chars, indexer, len, curr);
                    type = doNext(chars, indexer, curr);
                } else if (ParseUtil.isVar(curr)) {
                    ParseGetter.parseVar(chars, indexer, len, curr);
                    type = doNext(chars, indexer, curr);
                } else {
                    /*
                    type = ParseUtil.throwErr(chars, indexer);
                    */
                    type = CreateType.MAP;
                }
                break;
        }
        indexer.set(index);
        return type;
    }

    private final static CreateType doNext(char[] chars, IntAccessor indexer, int len) {
        return ParseUtil.skipWhitespaces(chars, indexer, len) == COLON ? CreateType.MAP : CreateType.LIST;
    }

    private final static AsRunner tryEmpty(char[] chars, IntAccessor indexer, int len, int curr) {
        if (curr == COLON) {
            int next = ParseUtil.skipWhitespaces(chars, indexer, len);
            ParseUtil.assertTrue(next == HUA_RIGHT, chars, indexer);
            return CreateType.MAP;
        } else if (curr == HUA_RIGHT) {
            return CreateType.LIST;
        }
        return null;
    }

    private final static CreateType defaultType(CreateType type) {
        return type == null ? CreateType.LIST : type;
    }

    /*
     * --------------------------------------------------------------
     * 构造器
     * --------------------------------------------------------------
     */

    private enum CreateType implements AsGetter, Supplier {
        MAP {
            @Override
            public Object get() {
                return new HashMap<>();
            }

            @Override
            public boolean test(Object o) {
                return true;
            }

            @Override
            public Object run(Object data) {
                return get();
            }
        },
        LIST {
            @Override
            public Object get() {
                return new ArrayList<>();
            }

            @Override
            public boolean test(Object o) {
                return true;
            }

            @Override
            public Object run(Object data) {
                return get();
            }
        }
    }
}

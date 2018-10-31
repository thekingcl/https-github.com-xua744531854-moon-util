package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiConsumer;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * @author benshaoye
 */
final class ParseCurly {
    private ParseCurly() {
        noInstanceError();
    }

    final static AsValuer parse(char[] chars, IntAccessor indexer, int len) {
        AsValuer valuer = null;
        int curr = ParseUtil.skipWhitespace(chars, indexer, len);
        if (curr == COLON) {
            ParseUtil.assertTrue(
                ParseUtil.skipWhitespace(chars, indexer, len) == HUA_RIGHT,
                chars, indexer);
            valuer = CreateType.MAP;
        } else if (curr == HUA_RIGHT) {
            valuer = CreateType.LIST;
        } else {
            AsValuer key = null;
            CreateType type = null;
            final LinkedList<BiConsumer> creators = new LinkedList<>();
            for (indexer.minus(); ; ) {
                curr = ParseUtil.skipWhitespace(chars, indexer, len);
                switch (curr) {
                    case SINGLE_QUOTE:
                    case DOUBLE_QUOTE:
                        valuer = ParseConst.parseStr(chars, indexer, curr);
                        break;
                    case COMMA:
                        type = defaultList(type);
                        valuer = valuer == null ? DataConst.get(null) : valuer;
                        // ...
                        valuer = null;
                        key = null;
                        break;
                    case COLON:
                        ParseUtil.assertTrue(type == CreateType.MAP, chars, indexer);
                        ParseUtil.assertNonNull(valuer, chars, indexer);
                        ParseUtil.assertNull(key, chars, indexer);

                        key = valuer.isConst() ? valuer : DataConst.get(valuer.toString());
                        valuer = null;
                        break;
                    case HUA_RIGHT:
                        if (valuer != null) {

                        }
                        break;
                }
            }
        }
        return valuer;
    }

    private final static CreateType defaultList(CreateType type) {
        return type == null ? CreateType.LIST : type;
    }

    /*
     * --------------------------------------------------------------
     * 构造器
     * --------------------------------------------------------------
     */

    private enum CreateType implements AsGetter {
        MAP {
            @Override
            public boolean test(Object o) {
                return true;
            }

            @Override
            public Object use(Object data) {
                return new HashMap<>();
            }
        },
        LIST {
            @Override
            public boolean test(Object o) {
                return true;
            }

            @Override
            public Object use(Object data) {
                return new ArrayList<>();
            }
        }
    }
}

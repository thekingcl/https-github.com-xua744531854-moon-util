package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * @author benshaoye
 */
final class ParseOpposite {
    private ParseOpposite() {
        noInstanceError();
    }

    final static AsRunner parse(char[] chars, IntAccessor indexer, int len) {
        AsRunner handler, linked;
        int curr = ParseUtil.skipWhitespaces(chars, indexer, len);
        switch (curr) {
            case YUAN_LEFT:
                handler = ParseGetter.parseYuan(chars, indexer, len);
                break;
            case FANG_LEFT:
                handler = ParseGetter.parseFang(chars, indexer, len);
                break;
            case HUA_LEFT:
                handler = ParseCurly.parse(chars, indexer, len);
                break;
            case CALLER:
                handler = ParseGetter.parseCaller(chars, indexer, len);
                break;
            default:
                if (ParseUtil.isNum(curr)) {
                    handler = ParseConst.parseNum(chars, indexer, len, curr);
                } else if (ParseUtil.isVar(curr)) {
                    handler = ParseGetter.parseVar(chars, indexer, len, curr);
                    ParseUtil.assertFalse(handler.isConst(), chars, indexer);
                } else {
                    handler = ParseUtil.throwErr(chars, indexer);
                }
                break;
        }
        ParseUtil.assertTrue(handler.isValuer(), chars, indexer);
        linked = ParseGetter.tryParseLinked(chars, indexer, len, handler);
        return linked.isConst()
            ? DataConst.getOpposite((DataConst) linked)
            : new DataGetterOpposite(linked);
    }
}

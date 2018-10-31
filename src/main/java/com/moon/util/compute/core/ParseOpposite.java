package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.FANG_LEFT;
import static com.moon.util.compute.core.Constants.YUAN_LEFT;

/**
 * @author benshaoye
 */
final class ParseOpposite {
    private ParseOpposite() {
        noInstanceError();
    }

    final static AsHandler parse(char[] chars, IntAccessor indexer, int len) {
        AsHandler handler;
        int curr = ParseUtil.skipWhitespaces(chars, indexer, len);
        switch (curr) {
            case YUAN_LEFT:
                handler = ParseGetter.parseYuan(chars, indexer, len);
                break;
            case FANG_LEFT:
                handler = ParseGetter.parseFang(chars, indexer, len);
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
        return tryParseLinkedOfOpposite(chars, indexer, len, handler);
    }

    private static final AsHandler tryParseLinkedOfOpposite(char[] chars, IntAccessor indexer, int len, AsHandler valuer) {
        AsHandler handler = ParseGetter.tryParseLinked(chars, indexer, len, valuer);
        return handler.isConst() ? DataConst.getOpposite((DataConst) handler) : new DataGetterOpposite(handler);
    }
}

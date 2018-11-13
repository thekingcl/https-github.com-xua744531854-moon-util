package com.moon.util.compute.core;

import com.moon.lang.SupportUtil;
import com.moon.lang.ref.IntAccessor;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
class ParseConst {
    private ParseConst() {
        noInstanceError();
    }

    final static AsValuer parseStr(char[] chars, IntAccessor indexer, int endChar) {
        return DataConst.get(SupportUtil.parseStr(chars, indexer, endChar));
    }

    final static AsValuer parseNum(char[] chars, IntAccessor indexer, int len, int current) {
        return DataConst.get(SupportUtil.parseNum(chars, indexer, len, current));
    }
}

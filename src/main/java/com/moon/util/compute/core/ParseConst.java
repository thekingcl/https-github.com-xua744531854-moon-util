package com.moon.util.compute.core;

import com.moon.enums.ArraysEnum;
import com.moon.lang.ref.IntAccessor;

import static com.moon.lang.SupportUtil.setChar;
import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.ParseUtil.toStr;

/**
 * @author benshaoye
 */
class ParseConst {
    private ParseConst() {
        noInstanceError();
    }

    final static AsValuer parseStr(char[] chars, IntAccessor indexer, int endChar) {
        char[] value = ArraysEnum.CHARS.empty();
        int index = 0, i = indexer.get();
        char ch;
        for (; (ch = chars[i++]) != endChar; ) {
            value = setChar(value, index++, ch);
        }
        indexer.set(i);
        return DataConst.get(toStr(value, index));
    }

    final static AsValuer parseNum(char[] chars, IntAccessor indexer, int len, int current) {
        char curr = (char) current;
        char[] value = {curr};
        int index = value.length, i = indexer.get();
        final Number number;
        for (; i < len && ParseUtil.isNum(curr = chars[i]); i++) {
            value = setChar(value, index++, curr);
        }
        if (curr == Constants.DOT) {
            final int cacheIndex = index, cacheI = i + 1;
            value = setChar(value, index++, curr);
            for (++i; i < len && ParseUtil.isNum(curr = chars[i]); i++) {
                value = setChar(value, index++, curr);
            }
            if (cacheI == i && ParseUtil.isVar(curr)) {
                i--;
                number = Integer.parseInt(toStr(value, cacheIndex));
            } else {
                number = Double.parseDouble(toStr(value, index));
            }
        } else {
            number = Integer.parseInt(toStr(value, index));
        }
        ParseUtil.setIndexer(indexer, i, len);
        return DataConst.get(number);
    }
}

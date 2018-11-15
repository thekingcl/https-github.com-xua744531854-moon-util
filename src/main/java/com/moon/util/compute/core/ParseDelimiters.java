package com.moon.util.compute.core;

import com.moon.lang.StringUtil;
import com.moon.lang.ref.ReferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
final class ParseDelimiters {
    private ParseDelimiters() {
        noInstanceError();
    }

    private final static Map<String, AsRunner> CACHE = ReferenceUtil.manageMap();

    final static AsRunner parse(String expression, String[] delimiters) {
        AsRunner parsed = CACHE.get(expression);
        if (parsed == null) {
            parsed = parseCore(expression, delimiters);
            synchronized (CACHE) {
                if (CACHE.get(expression) == null) {
                    CACHE.put(expression, parsed);
                }
            }
        }
        return parsed;
    }

    private static AsRunner parseCore(String expression, String[] delimiters) {
        String begin = StringUtil.requireNotBlank(delimiters[0]);
        String ender = StringUtil.requireNotBlank(delimiters[1]);
        final int length = expression.length(),
            beginLen = begin.length(), endLen = ender.length();
        int from = expression.indexOf(begin), to = expression.indexOf(ender);
        int one = 0, temp, size;
        if (from == 0 && to + endLen == length && expression.indexOf(begin, from + 1) < 0) {
            return ParseCore.parse(expression.substring(from + beginLen, to));
        } else if (from < 0) {
            return DataConst.get(expression);
        } else {
            List<AsRunner> list = new ArrayList<>();
            for (; from > 0; ) {
                if (from > one) {
                    list.add(DataConst.get(expression.substring(one, from)));
                }
                if (to > (temp = from + beginLen)) {
                    list.add(ParseCore.parse(expression.substring(temp, to)));
                }
                one = to + endLen;
                from = expression.indexOf(begin, one);
                to = expression.indexOf(ender, from);
            }
            if (one < length) {
                list.add(DataConst.get(expression.substring(one, length)));
            }
            if ((size = list.size()) == 0) {
                return DataConst.get(null);
            } else {
                AsRunner[] arr = list.toArray(new AsRunner[size]);
                AsRunner handler = new ParseRunAsHandler(arr);
                for (AsRunner current : arr) {
                    if (!current.isConst()) {
                        return handler;
                    }
                }
                return DataConst.get(handler.run());
            }
        }
    }

    private static class ParseRunAsHandler implements AsGetter {
        final AsRunner[] getters;

        private ParseRunAsHandler(AsRunner[] getters) {
            this.getters = getters;
        }

        @Override
        public Object run(Object data) {
            AsRunner[] getters = this.getters;
            int length = getters.length;
            StringBuilder builder = new StringBuilder(length * 16);
            Object item;
            for (int i = 0; i < length; i++) {
                item = getters[i].run(data);
                builder.append(item);
            }
            return builder.toString();
        }
    }
}

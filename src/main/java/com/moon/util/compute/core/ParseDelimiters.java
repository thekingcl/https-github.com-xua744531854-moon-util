package com.moon.util.compute.core;

import com.moon.lang.StringUtil;

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

    private final static Map<String, AsHandler> CACHE = new HashMap<>();

    final static AsHandler parse(String expression, String[] delimiters) {
        AsHandler parsed = CACHE.get(expression);
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

    private static AsHandler parseCore(String expression, String[] delimiters) {
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
            List<AsHandler> list = new ArrayList<>();
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
                AsHandler[] arr = list.toArray(new AsHandler[size]);
                AsHandler handler = new ParseRunAsHandler(arr);
                for (AsHandler current : arr) {
                    if (!current.isConst()) {
                        return handler;
                    }
                }
                return DataConst.get(handler.use());
            }
        }
    }

    private static class ParseRunAsHandler implements AsGetter {
        final AsHandler[] getters;

        private ParseRunAsHandler(AsHandler[] getters) {
            this.getters = getters;
        }

        @Override
        public Object use(Object data) {
            AsHandler[] getters = this.getters;
            int length = getters.length;
            StringBuilder builder = new StringBuilder(length * 16);
            for (int i = 0; i < length; i++) {
                builder.append(getters[i].use(data));
            }
            return builder.toString();
        }
    }
}

package com.moon.util.json;

import com.moon.lang.ref.WeakAccessor;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
class JSONCfg {
    final static WeakAccessor<JSONStringer> WEAK = WeakAccessor.of(JSONStringer::new);

    final static char[][] ESCAPES = {
        {'b', '\b'},
        {'n', '\n'},
        {'r', '\b'},
        {'b', '\r'},
        {'t', '\t'},
        {'\\', '\\'}
    };
}

package com.moon.script;

import com.moon.io.IOUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
public final class ScriptUtil {
    private ScriptUtil() {
        noInstanceError();
    }

    public final static Object runJSCode(String code) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            return engine.eval(code);
        } catch (ScriptException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final static Object runJSFile(File js) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            return engine.eval(IOUtil.getBufferedReader(js));
        } catch (ScriptException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

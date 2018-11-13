package com.moon.util;

import com.moon.lang.JoinerUtil;
import com.moon.util.support.PropertiesSupport;

import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.TypeUtil.cast;
import static com.moon.util.support.PropertiesSupport.getOrCatch;
import static com.moon.util.support.PropertiesSupport.getOrReload;

/**
 * @author benshaoye
 */
public final class PropertiesUtil {
    private PropertiesUtil() {
        noInstanceError();
    }

    public static final void refreshAll() {
        PropertiesSupport.refreshAll();
    }

    /**
     * get all properties
     *
     * @param path resources path or url
     * @return
     */
    public static final Map<String, String> get(String path) {
        return getOrReload(path);
    }

    @Deprecated
    public final static PropertiesGroup getGroup(String path) {
        return PropertiesGroup.of(path);
    }

    /*
     * -----------------------------------------------------------
     * get value
     * -----------------------------------------------------------
     */

    public static final String getString(String path, String key) {
        return get(path).get(key);
    }

    public static final int getIntValue(String path, String key) {
        return cast().toIntValue(getString(path, key));
    }

    public static final long getLongValue(String path, String key) {
        return cast().toLongValue(getString(path, key));
    }

    public static final double getDoubleValue(String path, String key) {
        return cast().toDoubleValue(getString(path, key));
    }

    public static final boolean getBooleanValue(String path, String key) {
        return cast().toBooleanValue(getString(path, key));
    }

    /*
     * -----------------------------------------------------------
     * get or default
     * -----------------------------------------------------------
     */

    public static final String getOrDefault(String path, String key, String defaultVal) {
        Map<String, String> map = getOrCatch(path);
        return (map != null && (key = map.get(key)) != null) ? key : defaultVal;
    }

    public static final int getOrDefault(String path, String key, int defaultVal) {
        Map<String, String> map = getOrCatch(path);
        return (map != null && (key = map.get(key)) != null)
            ? cast().toIntValue(key) : defaultVal;
    }

    public static final long getOrDefault(String path, String key, long defaultVal) {
        Map<String, String> map = getOrCatch(path);
        return (map != null && (key = map.get(key)) != null)
            ? cast().toLongValue(key) : defaultVal;
    }

    public static final double getOrDefault(String path, String key, double defaultVal) {
        Map<String, String> map = getOrCatch(path);
        return (map != null && (key = map.get(key)) != null)
            ? cast().toDoubleValue(key) : defaultVal;
    }

    public static final boolean getOrDefault(String path, String key, boolean defaultVal) {
        Map<String, String> map = getOrCatch(path);
        return (map != null && (key = map.get(key)) != null)
            ? cast().toBooleanValue(key) : defaultVal;
    }

    /*
     * -----------------------------------------------------------
     * get value
     * -----------------------------------------------------------
     */

    public static final String getString(String path, String... vars) {
        return get(path).get(JoinerUtil.join(vars, "."));
    }
}

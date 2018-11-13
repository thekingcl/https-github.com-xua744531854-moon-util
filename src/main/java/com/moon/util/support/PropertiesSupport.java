package com.moon.util.support;

import com.moon.io.IOUtil;
import com.moon.lang.ThrowUtil;
import com.moon.util.IteratorUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static java.lang.String.valueOf;

/**
 * @author benshaoye
 */
public final class PropertiesSupport {
    private PropertiesSupport() {
        ThrowUtil.noInstanceError();
    }

    private static final Map<String, HashMap<String, String>> CACHE = new ConcurrentHashMap<>();

    public static final void refreshAll() {
        CACHE.forEach((key, item) -> CACHE.compute(key, (k, v) -> {
            synchronized (PropertiesSupport.class) {
                return load(k);
            }
        }));
    }

    public static final HashMap<String, String> getOrReload(String path) {
        HashMap<String, String> hashMap = CACHE.get(path);
        if (hashMap == null) {
            synchronized (PropertiesSupport.class) {
                if ((hashMap = CACHE.get(path)) == null) {
                    hashMap = load(path);
                }
            }
        }
        return hashMap;
    }

    public static final HashMap<String, String> getOrCatch(String path) {
        try {
            return getOrReload(path);
        } catch (Throwable t) {
            return null;
        }
    }


    private static final HashMap<String, String> load(String path) {
        InputStream stream = IOUtil.getResourceAsStream(path);
        Properties properties = new Properties();
        try {
            properties.load(stream);
            HashMap<String, String> hashMap = syncToHashMap(properties);
            CACHE.put(path, hashMap);
            return hashMap;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static final HashMap<String, String> syncToHashMap(Map map) {
        HashMap<String, String> hashMap = new HashMap<>(map == null ? 16 : map.size());
        IteratorUtil.forEach(map, (key, value) -> hashMap.put(valueOf(key), valueOf(value)));
        return hashMap;
    }
}

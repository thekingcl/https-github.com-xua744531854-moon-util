package com.moon.util;

import com.moon.lang.ref.ReferenceUtil;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @author benshaoye
 */
public class PropertiesGroup {

    private final static Map<String, PropertiesGroup> CACHE = ReferenceUtil.manageMap();

    private final String key;
    private final PropertiesGroup parent;
    private Map<String, PropertiesGroup> children;
    private final Map<String, String> allProps;

    /*
     * -----------------------------------------------------------
     * constructor
     * -----------------------------------------------------------
     */

    public PropertiesGroup(String sourcePath) {
        sourcePath = requireNonNull(sourcePath);
        allProps = PropertiesUtil.get(sourcePath);
        this.key = sourcePath;
        this.parent = null;
        throw new UnsupportedOperationException();
    }

    private PropertiesGroup(PropertiesGroup parent, Map<String, String> allProps, String key) {
        this.key = requireNonNull(key);
        this.parent = requireNonNull(parent);
        this.allProps = requireNonNull(allProps);
        throw new UnsupportedOperationException();
    }

    /*
     * -----------------------------------------------------------
     * static constructor
     * -----------------------------------------------------------
     */

    static PropertiesGroup of(String path) {
        return CACHE.computeIfAbsent(path, PropertiesGroup::new);
    }

    public Map<String, String> getAll() {
        return allProps;
    }

    public PropertiesGroup getParent() {
        return parent;
    }

    public PropertiesGroup reload() {
        if (parent == null) {

        } else {
            parent.reload();
        }
        return this;
    }
}

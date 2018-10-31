package com.moon.util.support;

import com.moon.io.FileUtil;
import com.moon.lang.ThrowUtil;
import com.moon.lang.support.SystemSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 * @date 2018/9/12
 */
public final class ResourceSupport {

    static final char SEP = '/';

    private ResourceSupport() {
        noInstanceError();
    }

    public static boolean resourceExists(String path) {
        if (path == null) {
            return false;
        } else {
            URL url = get(path = FileUtil.formatPath(path));
            if (url == null && ((path = path.trim()).length() > 0)) {
                if (get(path) == null) {
                    if (get(path.charAt(0) == SEP ? path.substring(1) : SEP + path) == null) {
                        return new File(path).exists();
                    } else {
                        return true;
                    }
                }
            }
            return true;
        }
    }

    static URL get(String path) {
        return SystemSupport.class.getResource(path);
    }

    public static InputStream getResourceAsInputStream(String path) {
        URL url = getResourceURL(path);
        if (url == null) {
            throw new IllegalArgumentException(path);
        }
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static InputStream getResourceStreamOrNull(String path) {
        URL url = getResourceURL(path);
        try {
            return url == null ? null : url.openStream();
        } catch (IOException e) {
            return null;
        }
    }

    public static URL getResourceURL(String path) {
        URL url = get(path = FileUtil.formatPath(Objects.requireNonNull(path)));
        if (url == null && ((path = path.trim()).length() > 0)) {
            if ((url = get(path)) == null) {
                if ((url = get(path.charAt(0) == SEP ? path.substring(1) : SEP + path)) == null) {
                    try {
                        url = new URL("file:///" + path);
                    } catch (MalformedURLException e) {
                        return null;
                    }
                }
            }
        }
        return url;
    }
}

package com.moon.util.compute;

import com.moon.enums.ArraysEnum;
import com.moon.util.IteratorUtil;
import com.moon.util.MapperUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class RunnerDataMap extends HashMap {
    public RunnerDataMap(Object... dataArr) {
        for (Object data : dataArr) {
            if (data == null) {
                continue;
            } else if (data instanceof Map) {
                this.putAll((Map) data);
            } else if (data instanceof Iterable) {
                IteratorUtil.forEach((Iterable) data, (item, idx) -> this.put(idx, item));
            } else if (data.getClass().isArray()) {
                ArraysEnum.getOrObjects(data).forEach(data, (value, index) -> this.put(index, value));
            } else {
                MapperUtil.toMap(data, this);
            }
        }
    }
}

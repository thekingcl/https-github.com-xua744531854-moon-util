package com.moon.util.json;

import com.moon.lang.Buildable;
import com.moon.lang.ref.IntAccessor;
import com.moon.lang.ref.LongAccessor;
import com.moon.lang.ref.ReferenceUtil;
import com.moon.util.able.StringifyAble;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static com.moon.util.OptionalUtil.computeOrElse;

/**
 * @author benshaoye
 * @date 2018/9/14
 */
public class JSONStringBuilder implements Buildable<String>, StringifyAble {

    private final static IntAccessor NEXT_LENGTH = IntAccessor.of();
    private final static LongAccessor TOTAL = LongAccessor.of();
    private final static IntAccessor COUNT = IntAccessor.of();
    private final static Runnable runner = () -> {
        int value = (int) (TOTAL.get() / COUNT.get());
        synchronized (TOTAL) {
            NEXT_LENGTH.set(value);
        }
    };

    private static final String NULL = "null";

    private final Map<Object, String> hashCached = ReferenceUtil.manageMap();

    private String stringifyOfCollection(Collection collect) {
        return String.valueOf(collect);
    }

    private String stringifyOfArray(Object array) {
        Class type = array.getClass();
        return String.valueOf(array);
    }

    private String stringifyOfJavaBean(Object obj) {
        return String.valueOf(obj);
    }

    private String stringifyOfCalendar(Calendar calendar) {
        return computeOrElse(calendar, c -> String.valueOf(c.getTimeInMillis()), NULL);
    }

    private String stringifyOfDate(Date date) {
        return computeOrElse(date, d -> String.valueOf(d.getTime()), NULL);
    }

    private String stringifyOfMap(Map map) {
        return String.valueOf(map);
    }

    @Override
    public String stringify(Object obj) {
        return String.valueOf(obj);
    }

    @Override
    public String build() {
        return null;
    }
}

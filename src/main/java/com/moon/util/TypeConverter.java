package com.moon.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;

/**
 * type converter
 *
 * @author benshaoye
 * @date 2018/9/11
 */
public interface TypeConverter {
    /**
     * register type converter
     *
     * @param toType
     * @param func
     * @param <C>
     * @return
     */
    <C> TypeConverter register(Class<C> toType, BiFunction<Object, Class<C>, C> func);

    /**
     * register type converter if absent
     *
     * @param toType
     * @param func
     * @param <C>
     * @return
     */
    <C> TypeConverter registerIfAbsent(Class<C> toType, BiFunction<Object, Class<C>, C> func);

    /**
     * value to type
     *
     * @param value
     * @param type
     * @param <T>
     * @return
     */
    <T> T toType(Object value, Class<T> type);

    /**
     * value to boolean value
     *
     * @param value
     * @return
     */
    boolean toBooleanValue(Object value);

    /**
     * value to boolean
     *
     * @param value
     * @return
     */
    Boolean toBoolean(Object value);

    /**
     * value to char value
     *
     * @param value
     * @return
     */
    char toCharValue(Object value);

    /**
     * value to character
     *
     * @param value
     * @return
     */
    Character toCharacter(Object value);

    /**
     * value to byte value
     *
     * @param value
     * @return
     */
    byte toByteValue(Object value);

    /**
     * value to byte
     *
     * @param value
     * @return
     */
    Byte toByte(Object value);

    /**
     * value to short value
     *
     * @param value
     * @return
     */
    short toShortValue(Object value);

    /**
     * value to short
     *
     * @param value
     * @return
     */
    Short toShort(Object value);

    /**
     * value to int value
     *
     * @param value
     * @return
     */
    int toIntValue(Object value);

    /**
     * value to integer
     *
     * @param value
     * @return
     */
    Integer toInteger(Object value);

    /**
     * value to long value
     *
     * @param value
     * @return
     */
    long toLongValue(Object value);

    /**
     * value to long
     *
     * @param value
     * @return
     */
    Long toLong(Object value);

    /**
     * value to float value
     *
     * @param value
     * @return
     */
    float toFloatValue(Object value);

    /**
     * value to float
     *
     * @param value
     * @return
     */
    Float toFloat(Object value);

    /**
     * value to double value
     *
     * @param value
     * @return
     */
    double toDoubleValue(Object value);

    /**
     * value to double
     *
     * @param value
     * @return
     */
    Double toDouble(Object value);

    /**
     * value to big integer
     *
     * @param value
     * @return
     */
    BigInteger toBigInteger(Object value);

    /**
     * value to big decimal
     *
     * @param value
     * @return
     */
    BigDecimal toBigDecimal(Object value);

    /**
     * value to date
     *
     * @param value
     * @return
     */
    Date toDate(Object value);

    /**
     * value to sql date
     *
     * @param value
     * @return
     */
    java.sql.Date toSqlDate(Object value);

    /**
     * value to timestamp
     *
     * @param value
     * @return
     */
    Timestamp toTimestamp(Object value);

    /**
     * value to sql time
     *
     * @param value
     * @return
     */
    Time toTime(Object value);

    /**
     * value to calendar
     *
     * @param value
     * @return
     */
    Calendar toCalendar(Object value);

    /**
     * value to string
     *
     * @param value
     * @return
     */
    String toString(Object value);

    /**
     * value to stringBuilder
     *
     * @param value
     * @return
     */
    StringBuilder toStringBuilder(Object value);

    /**
     * value to stringBuffer
     *
     * @param value
     * @return
     */
    StringBuffer toStringBuffer(Object value);

    /**
     * value to enum
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends Enum<T>> T toEnum(Object value, Class<T> clazz);

    /**
     * value to java bean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T toBean(Map map, Class<T> clazz);

    /**
     * value to array
     *
     * @param value
     * @param arrayType
     * @return
     */
    <T> T toArray(Object value, Class<T> arrayType);

    /**
     * value to componentType array
     *
     * @param value
     * @param componentType
     * @return
     */
    <T> T[] toTypeArray(Object value, Class<T> componentType);

    /**
     * value to map
     *
     * @param value
     * @param mapClass
     * @param <T>
     * @return
     */
    <T extends Map> T toMap(Object value, Class<T> mapClass);

    /**
     * value to data
     *
     * @param value
     * @param listType
     * @param <T>
     * @return
     */
    <T extends List> T toList(Object value, Class<T> listType);

    /**
     * value to collection
     *
     * @param value
     * @param collectionType
     * @param <T>
     * @return
     */
    <T extends Collection> T toCollection(Object value, Class<T> collectionType);
}

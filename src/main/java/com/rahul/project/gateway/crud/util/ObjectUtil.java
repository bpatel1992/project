package com.rahul.project.gateway.crud.util;

import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.crud.core.ApplicationMap;
import com.rahul.project.gateway.crud.core.CastValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SystemComponent
public class ObjectUtil implements CastValue {

    private static final String get = "get";
    private final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
    // private static final String INTG_DATE_FORMAT = "dd/mm/yyyy";
    private Locale currentLocale = Locale.US;

    public static String getterForField(String key) {
        return get + key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    public <T> T objectForClass(Class<T> clz) {
        T t = null;
        try {
            t = clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    public Class<?> classForClassName(String className) {
        Class<?> clz = null;
        try {
            clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return clz;
    }

    public boolean isEmpty(String val) {
        return !this.isNonEmpty(val);
    }

    public boolean isNonEmpty(String val) {
        return val != null && !val.isEmpty() && !val.equalsIgnoreCase("");
    }

    public boolean isNonEmptyCollection(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public <K, V> boolean isNonEmptyMap(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    public Date toUtilDate(String dateStr) {
        if (this.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(INTG_DATE_FORMAT, currentLocale);
        try {
            date = dateFormatter.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    public java.sql.Date toSqlDate(String dateStr) {
        return this.isEmpty(dateStr) ? null : new java.sql.Date(toUtilDate(dateStr).getTime());
    }

    public java.sql.Date currentSqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public Date currentUtilDate() {
        return new Date(System.currentTimeMillis());
    }

    public String currentSqlDate(String format) {
        return new SimpleDateFormat(format).format(new java.sql.Date(System.currentTimeMillis()));
    }

    public String currentUtilDate(String format) {
        return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
    }

    public Date formattedDate(String srcDateString, String format) {
        Date formattedDate = null;
        try {
            formattedDate = new SimpleDateFormat(format).parse(srcDateString);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return formattedDate;
    }

    public java.sql.Date formattedSqlDate(String srcDateString, String format) {
        Date formattedDate = null;
        try {
            formattedDate = new SimpleDateFormat(format).parse(srcDateString);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return new java.sql.Date(formattedDate.getTime());
    }

    public <K, T> ApplicationMap<K, List<T>> groupBy(List<T> objList, String key) {
        ApplicationMap<K, List<T>> groupByMap = new ApplicationMap<>();
        objList.forEach(obj -> {
            K value = this.valueForKey(obj, key);
            List<T> tempList = groupByMap.get(value);
            if (tempList == null) {
                tempList = new ArrayList<>();
                groupByMap.put(value, tempList);
            }
            tempList.add(obj);
        });
        return groupByMap;
    }

    public String getFormattedKey(String key) {
        return key.contains(".") ? key.replaceAll(".", "_") : key;
    }

    public Object valueForKeyPath(Serializable dbObject, String key) {
        if (key.contains(".")) {
            StringTokenizer tokenizer = new StringTokenizer(key, ".");
            Object value = null;
            while (tokenizer.hasMoreTokens()) {
                value = valueForKeyPath(dbObject, tokenizer.nextToken());
                if (tokenizer.hasMoreTokens()) {
                    dbObject = (Serializable) value;
                }
            }
            return value;
        }
        return valueForKey(dbObject, key);
    }

    @SuppressWarnings("unchecked")
    public <K, T> K valueForKey(T obj, String key) {
        Object value = null;
        try {
            Method method = getValueForFieldGetter(obj, key);
            if (method == null) {
                Field field = getValueForField(obj, key);
                value = field.get(obj);
            } else {
                value = method.invoke(obj);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
        return (K) value;
    }

    public <T> Field getValueForField(T obj, String key) throws IllegalArgumentException, IllegalAccessException {
        Field field = null;
        try {
            field = ReflectionUtils.findRequiredField(obj.getClass(), key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return field;
    }

    public <T> Method getValueForFieldGetter(T obj, String key)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = null;
        try {
            method = ReflectionUtils.findRequiredMethod(obj.getClass(), getterForField(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return method;
    }
}

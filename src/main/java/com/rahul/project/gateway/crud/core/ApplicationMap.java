package com.rahul.project.gateway.crud.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper over Java Map Collection to handle some custom operations
 *
 * @param <K>
 * @param <V>
 * @author Rahul Malhotra
 */

public class ApplicationMap<K, V> extends LinkedHashMap<K, V> implements CastValue {


    public ApplicationMap() {
    }

    public ApplicationMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public Serializable objectForClass(Class<? extends Serializable> clz) {
        return new ObjectMapper().convertValue(this, clz);
    }

    @Override
    public <T> T castObject(String key, Class<T> clz) {
        T returnValue = null;
        Object value = this.get(key);
        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
            return null;
        }
        try {
            returnValue = castObject(value, clz);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public List<?> listValue(String key) {
        return (List<?>) this.get(key);
    }

    @SuppressWarnings("unchecked")
    public ApplicationMap<String, Object> hashForKey(String key) {
        Object value = this.get(key);
        return value != null ? new ApplicationMap<String, Object>((Map<? extends String, ? extends Object>) value) : null;
    }
}

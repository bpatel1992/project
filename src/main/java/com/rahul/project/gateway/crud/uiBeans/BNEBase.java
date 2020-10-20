package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.crud.core.CastValue;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

interface Deserialiser {

    default <T> Object deserialise(Object fromValue, TypeReference<T> toValueTypeRef) {
        return new ObjectMapper().convertValue(fromValue, toValueTypeRef);
    }
}

@NoArgsConstructor
@SystemComponent
public class BNEBase implements Serializable, Deserialiser, CastValue {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected <T> List<T> objectForClassFromList(Class<T> clz, List constructorParams) {
        List<T> list = new ArrayList<>();
        for (Object obj : constructorParams)
            list.add(objectForClass(clz, obj));
        return list;
    }

    private <T> T objectForClass(Class<T> clz, Object... consArgs) {
        T t = null;
        try {
            t = clz.getConstructor(parameterTypeList(consArgs)).newInstance(consArgs);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    private Class<?>[] parameterTypeList(Object... consArgs) {
        Class<?>[] parameterTypes = new Class<?>[consArgs.length];
        for (int i = 0; i < consArgs.length; i++) {
            Object obj = consArgs[i];
            parameterTypes[i] = obj.getClass();
        }
        return parameterTypes;
    }
}

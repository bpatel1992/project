package com.rahul.project.gateway.crud.annotation;

public @interface UIBeanToManyReference {

    String serialId();

    Class<?> toManyClass();

}

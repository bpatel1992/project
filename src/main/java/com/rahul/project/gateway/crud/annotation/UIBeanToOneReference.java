package com.rahul.project.gateway.crud.annotation;

public @interface UIBeanToOneReference {

    String serialId();

    Class<?> toOneClass();
}

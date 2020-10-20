package com.rahul.project.gateway.crud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UIBeanSpecifier {

    String id();

    Class<?> beanClass();

    String[] keys() default {};

    String[] ignoreKeys() default {};

    String parentBeanId() default "";

    UIBeanToOneReference[] toOneSetups() default {};

    UIBeanToManyReference[] toManySetups() default {};

}

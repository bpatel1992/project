package com.rahul.project.gateway.configuration.annotations;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@Validated
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Order(value = 3)
public @interface RESTController {

    /**
     * The value may indicate a suggestion for a logical component name, to be
     * turned into a Spring bean in case of an auto detected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}
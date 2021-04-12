package com.rahul.project.gateway.configuration.annotations;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {NullPointerException.class, Exception.class, Throwable.class}
        , isolation = Isolation.READ_COMMITTED
/* , noRollbackFor= {CustomException.class} */)
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public @interface RepositoryDao {

    /**
     * The value may indicate a suggestion for a logical component name, to be
     * turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}

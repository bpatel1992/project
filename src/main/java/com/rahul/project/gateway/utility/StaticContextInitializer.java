package com.rahul.project.gateway.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StaticContextInitializer {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void initialize() {
        TextSource.setMessageSource(messageSource);
        PropertySource.setEnvironment(environment);
    }
}

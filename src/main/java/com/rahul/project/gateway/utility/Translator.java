package com.rahul.project.gateway.utility;

import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@SystemComponent
public class Translator {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public String toLocale(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, null, locale);
    }

    public String toLocale(String msg, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, args, locale);
    }

    public String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }
}

package com.rahul.project.gateway.utility;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class TextSource {
    private static MessageSource messageSource;

    private TextSource() {
    }

    public static String getText(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public static String getText(String key, Object[] parameter) {
        return messageSource.getMessage(key, parameter, LocaleContextHolder.getLocale());
    }

    static void setMessageSource(MessageSource messageSource) {
        TextSource.messageSource = messageSource;
    }
}

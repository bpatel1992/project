package com.rahul.project.gateway.utility;

import org.springframework.core.env.Environment;

public class PropertySource {
    private static Environment environment;

    private PropertySource() {
    }

    public static String getRequiredProperty(String key) {
        return environment.getRequiredProperty(key);
    }

    static void setEnvironment(Environment environment) {
        PropertySource.environment = environment;
    }
}

package com.rahul.project.gateway.enums;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
public enum PatternConstants {
    TIME_PATTERN("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"),
    MOBILE_PATTERN("^[0-9]+$");
    private String patternValue;

    PatternConstants(final String patternValue) {
        this.patternValue = patternValue;
    }

    public String getValue() {
        return patternValue;
    }

}

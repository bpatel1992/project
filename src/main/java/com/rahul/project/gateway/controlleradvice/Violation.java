package com.rahul.project.gateway.controlleradvice;

import lombok.Data;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Data
public class Violation {

    private final String fieldName;

    private final String message;
/*
    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }*/
}

package com.rahul.project.gateway.controlleradvice;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Data
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}

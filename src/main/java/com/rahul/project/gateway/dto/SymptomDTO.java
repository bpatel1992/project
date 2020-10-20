package com.rahul.project.gateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * @Date 2019-04-30
 */
@Data
public class SymptomDTO {

    private String symptom;

    @NotNull(message = "Validator.notNullMsg")
    private String petType;
}

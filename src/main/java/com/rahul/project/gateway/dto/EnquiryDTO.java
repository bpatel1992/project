package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EnquiryDTO {
    private Long countryId;
    private Long mobile;
    @NotNull(message = "Validator.notNullMsg")
    private String email;
    @NotNull(message = "Validator.notNullMsg")
    private Long titleId;
    @NotNull(message = "Validator.notNullMsg")
    private String name;
    @NotNull(message = "Validator.notNullMsg")
    private String message;
    private String userName;
}

package com.rahul.project.gateway.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.utility.Patterns;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddClientDto {
    private Long id;
    private Long countryId;
    @Pattern(regexp = Patterns.Validator_EmailRegEx, message = "Validator.EmailRegMsg")
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private Long createdByPartnerId;
    @NotNull
    private Long createdByUserId;
    private Long genderId;
    private Long titleId;
    private String randomKey;
    @NotNull
    private String mobile;

}

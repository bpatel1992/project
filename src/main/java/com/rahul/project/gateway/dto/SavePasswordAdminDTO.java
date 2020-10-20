package com.rahul.project.gateway.dto;

import com.rahul.project.gateway.utility.Patterns;
import lombok.Data;

import javax.validation.constraints.Pattern;


@Data
public class SavePasswordAdminDTO {

    @Pattern(regexp = Patterns.Validator_passwordRegEx, message = "Validator.EmailRegMsg")
    private String password;

    private String randomKey;

    private String userType;

    private String signUpBy;
}

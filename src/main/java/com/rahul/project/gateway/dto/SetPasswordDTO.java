package com.rahul.project.gateway.dto;

import com.rahul.project.gateway.utility.Patterns;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class SetPasswordDTO {
    @Pattern(regexp = Patterns.Validator_passwordRegEx, message = "validator.password.reg.msg")
    private String password;
    @Pattern(regexp = Patterns.Validator_passwordRegEx, message = "validator.password.reg.msg")
    private String confirmPassword;
    @Pattern(regexp = Patterns.Validator_passwordRegEx, message = "validator.password.reg.msg")
    private String oldPassword;
}

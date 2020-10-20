package com.rahul.project.gateway.dto;

import com.rahul.project.gateway.utility.Patterns;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author rahul malhotra
 * date 2019-08-09
 */
@Data
@ApiModel(description = "All details about the sign up of a customer stage 1")
public class SignUpCustomerStage1Dto {
    @NotNull(message = "Validator.mandatory")
    private String firstName;
    @NotNull(message = "Validator.mandatory")
    private String lastName;
    @Pattern(regexp = Patterns.Validator_EmailRegEx, message = "Validator.EmailRegMsg")
    private String email;
    private String mobile;
    private Long countryId;
    private Long genderId;
    private Long titleId;
    @NotNull(message = "Validator.mandatory")
    private String timeZone;
    @NotNull(message = "Validator.mandatory")
    private String userType;
    @NotNull(message = "Validator.mandatory")
    private String signUpBy;
    @NotNull(message = "Validator.mandatory")
    private String password;
}

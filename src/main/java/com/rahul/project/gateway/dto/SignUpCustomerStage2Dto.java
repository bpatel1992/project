package com.rahul.project.gateway.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * date 2019-08-09
 */
@Data
@ApiModel(description = "All details about the sign up of a customer ")
public class SignUpCustomerStage2Dto {

    @NotNull(message = "Validator.mandatory")
    private String randomKey;

    @NotNull(message = "Validator.mandatory")
    private String otp;

    @NotNull(message = "Validator.mandatory")
    private String timeZone;
}

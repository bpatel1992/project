package com.rahul.project.gateway.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rahul malhotra
 * date 2019-08-09
 */
@Data
@ApiModel(description = "All details about the sign up of a customer ")
public class SignUpCustomerStage3Dto {

    @ApiModelProperty(notes = "userId of the user", name = "user id")
    private Long id;

    private String message;

    private String password;

}
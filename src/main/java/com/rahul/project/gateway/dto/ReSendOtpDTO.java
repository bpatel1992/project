package com.rahul.project.gateway.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
public class ReSendOtpDTO {

    @NotNull(message = "Validator.notNullMsg")
    private String randomKey;

    @NotNull(message = "Validator.notNullMsg")
    private String timeZone;
}



package com.rahul.project.gateway.dto;


import com.rahul.project.gateway.utility.Patterns;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
public class VerifyOtpDTO {

    @Pattern(regexp = Patterns.DECIMAL_PATTERN, message = "Validator.MobileNoRegMsg")
    private String mobileNo;

    @NotNull(message = "Validator.notNullMsg")
    private String otp;

    private String identifier;

    //    @NotNull(message = "Validator.notNullMsg")
    private String userType;

    //    @NotNull(message = "Validator.notNullMsg")
//    @Pattern(regexp = Patterns.TIME_PATTERN, message = "Validator.MobileNoRegMsg")
    private String time;

    private Long countryId;

    private String randomKey;

    private String timeZone;
}



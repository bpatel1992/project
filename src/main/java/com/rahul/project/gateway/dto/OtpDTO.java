package com.rahul.project.gateway.dto;


import com.rahul.project.gateway.utility.Patterns;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
public class OtpDTO {

    @Pattern(regexp = Patterns.DECIMAL_PATTERN, message = "Validator.MobileNoRegMsg")
    private String mobileNo;

    private String userName;

    @Pattern(regexp = Patterns.Validator_EmailRegEx, message = "Validator.EmailRegMsg")
    private String email;

    private String identifier;

    private String randomKey;

    private String timeZone;

    private String userType;

    private String serviceName;

    //    @Pattern(regexp = Patterns.TIME_PATTERN, message = "Validator.MobileNoRegMsg")
    private String time;

    private Long countryId;
}



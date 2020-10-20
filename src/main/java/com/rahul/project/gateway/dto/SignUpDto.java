package com.rahul.project.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
public class SignUpDto {

    @ApiModelProperty(notes = "userId of the user", name = "user id")
    private Long id;

    private int stage;

    private String userName;

    //    @Email(regexp = Patterns.Validator_EmailRegEx, message = "Validator.EmailRegMsg")
    private String email;

    private boolean isEmailVerified;

    //    @NotEmpty(message = "Validator.mandatory")
//    @Pattern(regexp = Patterns.Validator_passwordRegEx, message = "Validator.passwordRegMsg")
    private String password;

    //    @NotEmpty(message = "Validator.mandatory")
    private String fullName;

    //    @Pattern(regexp = Patterns.DECIMAL_PATTERN, message = "Validator.MobileNoRegMsg")
    private String mobile;

    private Long countryId;

    private Long genderId;

    //    @NotNull(message = "Validator.mandatory")
    private Long userLoginType;

    private boolean isMobileVerified;

    private String dob;

    private String profilePicURL;

    private boolean isSocialSignUp;

    private Set<AuthorityDTO> authorities;

}
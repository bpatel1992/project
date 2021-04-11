package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnquiryDTO {
    private Long countryId;
    @NotNull(message = "Validator.notNullMsg")
    private String timeZone;
    private String mobile;
    //    @NotNull(message = "Validator.notNullMsg")
    private String email;
    //    @NotNull(message = "Validator.notNullMsg")
    private Long titleId;
    //    @NotNull(message = "Validator.notNullMsg")
    private String name;
    @NotNull(message = "Validator.notNullMsg")
    private String message;
    private Long attendantId;
    private Long customerId;
    private String userName;
    private String phone;
    private String nameFormatted;
    private Date creationDate;
    private Date modificationDate;
}

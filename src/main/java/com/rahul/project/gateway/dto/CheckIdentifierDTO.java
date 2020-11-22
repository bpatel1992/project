package com.rahul.project.gateway.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
public class CheckIdentifierDTO {

    @NotNull(message = "Validator.notNullMsg")
    private String identifier;
    private Long countryId;
    private String userType;

}



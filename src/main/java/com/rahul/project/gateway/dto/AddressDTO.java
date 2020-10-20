package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    private Long id;

    @NotNull(message = "Validator.notNullMsg")
    private String address;

    private Double latitude;
    private Double longitude;

    private String status;
}

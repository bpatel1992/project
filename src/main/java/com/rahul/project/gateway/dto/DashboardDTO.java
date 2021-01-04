package com.rahul.project.gateway.dto;


import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class DashboardDTO {

    @NotNull(message = "Validator.notNullMsg")
    private String currency;

    @ApiParam("Send day for 1 day ,month for 1 month,year for 1 year")
    @NotNull(message = "Validator.notNullMsg")
    private String filter;

    @NotNull(message = "Validator.notNullMsg")
    private String currentDateTime;
}



package com.rahul.project.gateway.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class DashboardTxnSummaryDTO {

    @NotNull(message = "Validator.notNullMsg")
    private String currency;

}



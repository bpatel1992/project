package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionProcessDTO {

    @NotNull(message = "Validator.notNullMsg")
    private Long customerId;

    @NotNull(message = "Validator.notNullMsg")
    private BigDecimal amount;

    @NotNull(message = "Validator.notNullMsg")
    private Long serviceId;

    private String redirectURL;

    @NotNull(message = "Validator.notNullMsg")
    private Long appointmentId;

//    @NotNull(message = "Validator.notNullMsg")
//    private String transactionType;


    private BigDecimal fee;

    private BigDecimal tax;

    private BigDecimal payableAmount;

    private String transactionGatewayReferenceId;

    private String gatewayName;

    private String bankName;

    // private Long referenceId;


    private String Status;


    private String respKey;
    private String pgKey;
    private String transactionId;
    private String transactionTime;

}

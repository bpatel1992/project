package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionProcessDTO {

    private Long id;
    @NotNull(message = "Validator.notNullMsg")
    private Long customerId;
    private BigDecimal amount;
    @NotNull(message = "Validator.notNullMsg")
    private Long serviceId;
    private Long packageId;
    private Long txnTypeId;
    private String redirectURL;
    private Long appointmentId;
    private Long authorityId;
    private BigDecimal fee;
    private BigDecimal tax;
    private BigDecimal payableAmount;
    private String transactionGatewayReferenceId;
    private String gatewayName;
    private String bankName;
    private String Status;
    @NotNull(message = "Validator.mandatory")
    private String timeZone;
    private String respKey;
    private String pgKey;
    private String transactionId;
    private String logDate;
    private HashMap<String, Object> attributes;
}

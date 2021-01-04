package com.rahul.project.gateway.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TxnSummary {

    private long totalTxn;
    private BigDecimal totalAmount;
    private BigDecimal operatorEarning;
    private BigDecimal operatorLiability;

}

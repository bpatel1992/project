package com.rahul.project.gateway.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class TxnDetailResponse {

    private Integer displayOrder;

    private String rangeLabel;

    private String txnsAmount;

    private String noOfTxns;

    private Long noOfTransactions;

    private BigDecimal transactionAmount;
}

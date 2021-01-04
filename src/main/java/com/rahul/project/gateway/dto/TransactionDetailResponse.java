package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class TransactionDetailResponse {

    private String transactionAmountLabel;

    private String transactionAmountColor;

    private String noOfTxnLabel;

    private String noOfTxnColor;

    private Set<TxnDetailResponse> txnDetailResponses;
}

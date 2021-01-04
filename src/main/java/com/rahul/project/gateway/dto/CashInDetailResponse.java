package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class CashInDetailResponse {

    private String cashInAmountLabel;

    private String cashInAmountColor;

    private String noOfCashInLabel;

    private String noOfCashInColor;

    private Set<TxnDetailResponse> txnDetailResponses;
}

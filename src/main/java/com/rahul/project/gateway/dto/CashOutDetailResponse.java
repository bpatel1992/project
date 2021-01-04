package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class CashOutDetailResponse {

    private String cashOutAmountLabel;

    private String cashOutAmountColor;

    private String noOfCashOutLabel;

    private String noOfCashOutColor;

    private Set<TxnDetailResponse> txnDetailResponses;
}

package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class CommissionInDetailResponse {

    private String commissionInAmountLabel;

    private String commissionInAmountColor;

    private String noOfCommissionInLabel;

    private String noOfCommissionInColor;

    private Set<TxnDetailResponse> txnDetailResponses;
}

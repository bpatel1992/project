package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-04
 */
@Data
public class CommissionOutDetailResponse {

    private String commissionOutAmountLabel;

    private String commissionOutAmountColor;

    private String noOfCommissionOutLabel;

    private String noOfCommissionOutColor;

    private Set<TxnDetailResponse> txnDetailResponses;
}

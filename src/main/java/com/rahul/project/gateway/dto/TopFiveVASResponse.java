package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.Set;

/**
 * @author rahul malhotra
 * date 2020-03-05
 */
@Data
public class TopFiveVASResponse {

    private String vASCodeLabel;
    private String vASNameLabel;
    private String vASNoOfTxnLabel;
    private String vASTotalAmountLabel;

    private Set<TopFiveVASDetailResponse> txnDetailResponses;
}

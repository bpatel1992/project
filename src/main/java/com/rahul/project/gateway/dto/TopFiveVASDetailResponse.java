package com.rahul.project.gateway.dto;


import lombok.Data;

/**
 * @author rahul malhotra
 * date 2020-03-05
 */
@Data
public class TopFiveVASDetailResponse {

    private String vASCode;
    private String vASName;
    private String vASNoOfTxn;
    private String vASTotalAmount;
    private int displayOrder;
}

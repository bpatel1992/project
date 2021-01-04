package com.rahul.project.gateway.dto;


import lombok.Data;

/**
 * @author rahul malhotra
 * date 2020-03-05
 */
@Data
public class TopFiveMerchantDetailResponse {

    private long merchantUserId;
    private String merchantName;
    private int merchantNoOfTxn;
    private String merchantTotalAmount;
    private int displayOrder;
}

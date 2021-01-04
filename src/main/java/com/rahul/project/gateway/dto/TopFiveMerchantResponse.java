package com.rahul.project.gateway.dto;


import lombok.Data;

import java.util.List;

/**
 * @author rahul malhotra
 * date 2020-03-05
 */
@Data
public class TopFiveMerchantResponse {

    private String responseCode;
    private String responseMessage;

    private List<TopFiveMerchantDetailResponse> merchantDetailResponse;
}

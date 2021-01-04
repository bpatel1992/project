package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecentFundTransferRequest {
    int pageNo;
    int pageSize;
}

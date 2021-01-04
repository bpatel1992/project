package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecentFundTransfers {

    private String responseCode;
    private String responseMessage;
    private List<RecentFundTransferDetails> recentFundTransferDetailsList;
}

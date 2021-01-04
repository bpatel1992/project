package com.rahul.project.gateway.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDashboardResponse {

    private String walletColor;

    private String walletName;

    private String WalletAmount;

    private BigDecimal amount;
}

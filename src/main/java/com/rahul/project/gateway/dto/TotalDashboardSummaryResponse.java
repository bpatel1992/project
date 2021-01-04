package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TotalDashboardSummaryResponse {

    private BigDecimal totalSales;
    private BigDecimal totalEarning;

}

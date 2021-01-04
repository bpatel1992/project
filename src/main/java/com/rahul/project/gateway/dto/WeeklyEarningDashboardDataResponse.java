package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WeeklyEarningDashboardDataResponse {

    private String weekName;
    private BigDecimal amount;
    private String color;
}


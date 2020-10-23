package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class FeeDTO {

    private long id;

    private Long servicesId;//request param

    private Long authorityId;//request param

    private BigDecimal fee;

    private BigDecimal tax;

    private BigDecimal amount;//request param

    private BigDecimal payableAmount;

    private String taxType;

    private String feeType;


}

package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FeeDTO {

    private Long servicesId;

    private long id;
    private Long authorityId;
    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal tax;

    public FeeDTO(Long servicesId, Long authorityId, BigDecimal amount) {
        this.servicesId = servicesId;
        this.authorityId = authorityId;
        this.amount = amount;
    }

    private BigDecimal payableAmount;

    private String taxType;

    private String feeType;


}

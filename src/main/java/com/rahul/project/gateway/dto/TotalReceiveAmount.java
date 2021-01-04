package com.rahul.project.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalReceiveAmount {
    private Double amount;
    private String responseCode;
    private String responseMessage;
}

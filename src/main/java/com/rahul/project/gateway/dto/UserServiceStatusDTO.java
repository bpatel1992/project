package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserServiceStatusDTO {

    private Long userId;
    private Long serviceId;
    private Date validityFromDate;
    private Date validityToDate;
    private String plan;
    private String timeZone;
    private Boolean status;
}

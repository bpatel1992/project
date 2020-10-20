
package com.rahul.project.gateway.dto;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TimeRange {

    private Long displayOrder;
    private Long fromHours;
    private Long fromMinutes;
    private Long id;
    private Long toHours;
    private Long toMinutes;

}

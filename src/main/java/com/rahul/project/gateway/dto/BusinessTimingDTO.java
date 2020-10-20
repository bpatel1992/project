
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessTimingDTO {

    private Boolean friday;
    private Long id;
    private List<TimeRange> timeRange;
    private List<DayDTO> days;

}

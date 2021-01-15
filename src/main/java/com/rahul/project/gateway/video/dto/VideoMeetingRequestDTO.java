package com.rahul.project.gateway.video.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoMeetingRequestDTO {

    private Long appointmentId;
    private String userType;
}

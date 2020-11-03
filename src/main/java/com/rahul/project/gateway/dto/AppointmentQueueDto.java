package com.rahul.project.gateway.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentQueueDto {
    private Long appointmentId;
    private String dogName;
    private String ownerName;
    private String appointmentType;
    private String appointmentFromTime;
    private String appointmentToTime;
    private String status;
    private String imageUrl;
    private String slot;
}

package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentAvailabilitySlotDto {

    private String slot;
    private List<AppointmentAvailabilityDto> appointmentAvailabilityDtos;
}

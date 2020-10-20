package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAppointmentDto {
    private Long id;
    private Long appointmentReasonId;
    private Long appointmentTypeId;
    private Long attendantId;
    private Long statusTypeId;
    private Long partnerId;
    private Long PetId;
    private Long customerId;
    private Long clinicId;
    private String appointmentReasonOthers;
    private Integer occurrence;
    private Long appointmentRepeatId;
    //yyyy-MM-dd
    private String date;
    // hh:mm:ss 24 hour format
    private String fromTime;
    private String toTime;
    private Integer slotInMin;
    private List<AppointmentAvailabilitySlotDto> appointmentAvailabilitySlotDtos;
}

package com.rahul.project.gateway.dto.appointmentdashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.dto.Pet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentCardDTO{
    private String ownerName;
    private String petName;
    private Long petId;
    private Long customerId;
    private String fromTime;
    private String toTime;
    private Long addressTypeId;
    private String addressTypeName;
    private String imageName;
}

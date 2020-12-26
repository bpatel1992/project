package com.rahul.project.gateway.dto.appointmentdashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressTypeAppointmentDTO{
    private Long addressTypeId;
    private String addressTypeName;
}

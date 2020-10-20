
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {
    private MasterDataDTO appointmentReason;
    private MasterDataDTO appointmentType;
    private MasterDataDTO appointmentRepeat;
    private List<Client> clients;
    private String email;
    private String lastVisit;
    private List<Pet> pets;
    private String phone;
    private String appointmentDate;
    private String appointmentFromTime;
    private String appointmentToTime;
}

package com.rahul.project.gateway.dto.appointmentdashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.dto.Pet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDashboardDto {
    private Long attendantId;
    private int totalAppointments;
    private int totalPendingAppointments;
    private double bookingMeter;
    private List<AddressTypeAppointmentDTO> ocAppointments;
    private List<AddressTypeAppointmentDTO> cAppointments;
    private List<AddressTypeAppointmentDTO> hAppointments;
    private List<AppointmentCardDTO> upcomingAppointmentDTOS;
    private List<AppointmentCardDTO> liveAppointmentDTOS;
    //yyyy-MM-dd
    private String date;
    private String fromTime;
    private Integer slotInMin;

    public AddressTypeAppointmentDTO getAddressTypeAppointmentDTO(){
        return new AddressTypeAppointmentDTO();
    }

    public AppointmentCardDTO getAppointmentCardDTO(){
        return new AppointmentCardDTO();
    }
}
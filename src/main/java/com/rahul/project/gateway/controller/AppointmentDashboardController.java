package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.dto.appointmentdashboard.AppointmentDashboardDto;
import com.rahul.project.gateway.service.AppointmentDashboardService;
import com.rahul.project.gateway.service.EnableXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RESTController
public class AppointmentDashboardController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AppointmentDashboardService appointmentDashboardService;

    @RequestMapping(path = {"/oauth2/api/dashboard/getAppointmentsByDate"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDashboardDto appointments(@Valid @RequestBody AppointmentDashboardDto createAppointmentDto) throws Exception {
        return appointmentDashboardService.appointments(createAppointmentDto);
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/getUpcomingAppointmentsByDate"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDashboardDto upcomingAppointments(@Valid @RequestBody AppointmentDashboardDto createAppointmentDto) throws Exception {
        return appointmentDashboardService.upcomingAppointments(createAppointmentDto);
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/getLiveAppointmentsByDate"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDashboardDto liveAppointments(@Valid @RequestBody AppointmentDashboardDto createAppointmentDto) throws Exception {
        return appointmentDashboardService.liveAppointments(createAppointmentDto);
    }

}

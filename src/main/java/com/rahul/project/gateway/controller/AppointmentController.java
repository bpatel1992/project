package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.dto.ResponseHandlerDTO;
import com.rahul.project.gateway.dto.enablex.CreateRoomDTO;
import com.rahul.project.gateway.service.AppointmentService;
import com.rahul.project.gateway.service.EnableXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RESTController
public class AppointmentController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    private EnableXService enableXService;

    @PostMapping("/create/room/video")
    public ResponseHandlerDTO<?> createRoomVideo(@RequestBody CreateRoomDTO createRoomDTO) throws Exception {
        return enableXService.createRoom(createRoomDTO);
    }

    @RequestMapping(path = {"/oauth2/api/create/appointment", "/oauth2/api/update/appointment"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto createAppointment(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.createAppointment(createAppointmentDto);
    }

    @RequestMapping(path = {"/oauth2/api/appointment/schedule"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto getSchedule(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.getAvailability(createAppointmentDto);
    }

    @RequestMapping(path = {"/oauth2/api/appointment/getAvailableDatesForNext7days"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto getAvailableDates(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.getAvailableDatesForNext7days(createAppointmentDto);
    }

    @RequestMapping(path = {"/oauth2/api/appointment/updateCustomerArrivalStatus"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCustomerArrivalStatus(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.updateCustomerArrivalStatus(createAppointmentDto);
    }

}

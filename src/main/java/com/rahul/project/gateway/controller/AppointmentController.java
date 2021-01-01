package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.dto.ResponseHandlerDTO;
import com.rahul.project.gateway.dto.enablex.CreateRoomDTO;
import com.rahul.project.gateway.service.AppointmentService;
import com.rahul.project.gateway.service.EnableXService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Create room video " + "\n" +
                "2. Create appointment for user" + "\n" +
                "3. Fetch available booking slots" + "\n" +
                "4. Fetch next 7 available working day for partner and clinic" + "\n" +
                "5. Updates customer arrival status for appointment",tags = { "Appointment services" })
public class AppointmentController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    private EnableXService enableXService;

    @ApiOperation(value ="Create room video", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/create/room/video")
    public ResponseHandlerDTO<?> createRoomVideo(@RequestBody CreateRoomDTO createRoomDTO) throws Exception {
        return enableXService.createRoom(createRoomDTO);
    }

    @ApiOperation(value ="create appointment for user", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/create/appointment", "/oauth2/api/update/appointment"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto createAppointment(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.createAppointment(createAppointmentDto);
    }

    @ApiOperation(value ="Fetch available slots for given attendant Id, clinic Id and date", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/appointment/schedule"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto getSchedule(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.getAvailability(createAppointmentDto);
    }

    @ApiOperation(value ="Fetch next seven available dates for given attendant Id, clinic Id and date", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/appointment/getAvailableDatesForNext7days"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateAppointmentDto getAvailableDates(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.getAvailableDatesForNext7days(createAppointmentDto);
    }

    @ApiOperation(value ="Update customer arrival status for given appointmentId", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(path = {"/oauth2/api/appointment/updateCustomerArrivalStatus"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCustomerArrivalStatus(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {
        return appointmentService.updateCustomerArrivalStatus(createAppointmentDto);
    }

}

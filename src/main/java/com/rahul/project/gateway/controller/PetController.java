package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.ResponseHandlerDTO;
import com.rahul.project.gateway.dto.pet.CreatePetDTO;
import com.rahul.project.gateway.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

/**
 * @author rahul malhotra
 * @version 1.0
 * @Date 2019-04-30
 */

@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Register Pet details by admin",tags = { "Pet services" })
public class PetController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    PetService petService;

    @ApiOperation(value = "Register Pet details by admin", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @PostMapping(value = "/oauth2/api/pet/admin", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePetDTO createPetAdmin(@Valid @RequestBody CreatePetDTO createPetDTO) throws Exception {
        return petService.registerPetAdmin(createPetDTO);
    }

    @ApiOperation(value = "Get Register Pet details of Customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})
    @GetMapping(value = "/oauth2/api/pet/fetch", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseHandlerDTO getPetDetails() throws Exception {
        return new ResponseHandlerDTO();
    }

}

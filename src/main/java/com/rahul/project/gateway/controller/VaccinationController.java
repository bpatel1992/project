package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.model.Vaccination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1.  Vaccination details of pet", tags = {"Vaccination services"})
public class VaccinationController {

    @ApiOperation(value = "Register vaccination details of pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/oauth2/api/vaccination", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vaccination addVaccinationDetails(@Valid @RequestBody Vaccination vaccination){

    }

}

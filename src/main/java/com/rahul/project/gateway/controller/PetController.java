package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.pet.CreatePetDTO;
import com.rahul.project.gateway.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class PetController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    PetService petService;

    @PostMapping(value = "/oauth2/api/pet/admin", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreatePetDTO createPetAdmin(@Valid @RequestBody CreatePetDTO createPetDTO) throws Exception {
        return petService.registerPetAdmin(createPetDTO);
    }

}

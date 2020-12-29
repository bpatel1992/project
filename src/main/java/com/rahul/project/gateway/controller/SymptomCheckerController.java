package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.SymptomDTO;
import com.rahul.project.gateway.model.Symptom;
import com.rahul.project.gateway.service.SymptomCheckerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2020-03-24
 */
@RESTController
@RequestMapping({"/oauth2/api/sc", "/api/sc"})
public class SymptomCheckerController {

    @Autowired
    SymptomCheckerService symptomCheckerService;

    @ApiOperation(value = "Fetch symptom details", produces = MediaType.TEXT_HTML_VALUE)
    @PostMapping(value = "/get-symptoms", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Symptom> getSymptoms(@RequestBody SymptomDTO symptom) throws Exception {
        return symptomCheckerService.getSymptoms(symptom.getSymptom());
    }

}

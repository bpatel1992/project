package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.service.PartnerClientManagementService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Save client mapping details" + "\n" +
                "2. Fetch client mapping details" + "\n" +
                "3. Fetch client list by partner Id, pagination is applied for this api",tags = { "Partner client services" })
public class PartnerClientManagementController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    PartnerClientManagementService partnerClientManagementService;

    @Autowired
    CommonUtility commonUtility;

    @ApiOperation(value = "Save client mapping details")
    @RequestMapping(path = {"/oauth2/partner/saveClient"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean saveClientDetails(@Valid @RequestBody AddClientDto userDTO) throws Exception {
        return partnerClientManagementService.saveClientMapping(userDTO);
    }

    @ApiOperation(value = "Fetch client details", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/api/partner/fetchClientDetails"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO fetchClientDetails(@Valid @RequestBody AddClientDto addClientDto) throws Exception {
        return partnerClientManagementService.fetchClientDetails(addClientDto);
    }

    @ApiOperation(value = "Fetch client list by partner Id, pagination is applied for this api", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/api/partner/fetchClientListByPartnerId"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<AddClientDto> fetchClientListByPartnerId(@Valid @RequestBody AddClientDto addClientDto) throws Exception {
        return partnerClientManagementService.fetchClientListByPartnerId(addClientDto);
    }

}

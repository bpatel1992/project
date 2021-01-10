package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.service.PartnerService;
import com.rahul.project.gateway.utility.Translator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Register partner details, " + "\n" +
                "2. Save partner business timings, " + "\n" +
                "3. Save partner address details, "+ "\n" +
                "4. Fetch partner address by location",tags = { "Partner services" })
public class PartnerController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Translator translator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PartnerService partnerService;

    @ApiOperation(value = "Register partner details", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/add-update-partner", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerPartner(@Valid @RequestBody PartnerDTO partnerDTO) throws Exception {
        logger.info("inside registerPartner  !!");
        partnerService.registerPartner(convertToEntity(partnerDTO));
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(translator.toLocale("partner.create.success"));
        return responseDTO;
    }

    @ApiOperation(value = "Save partner business timings", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/user/partner/update/create", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO userCreateBusinessTimings() throws Exception {
        logger.info("inside registerPartner  !!");
        return partnerService.userUpdateCreate(new ResponseDTO());
    }

    @ApiOperation(value = "Save partner address details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/partner/address/update/create", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PartnerAddressDTO createUpdatePartnerAddress(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.createUpdatePartnerAddress(partnerAddressDTO, new ResponseDTO());
    }

    @ApiOperation(value = "Save partner address details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/partner/address/fetch", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<PartnerAddressDTO> fetchPartnerAddress(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.fetchPartnerAddress(partnerAddressDTO, new ResponseDTO());
    }

    @ApiOperation(value = "delete partner address details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/partner/address/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deletePartnerAddress(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) throws Exception {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.deletePartnerAddress(partnerAddressDTO, new ResponseDTO());
    }

    @ApiOperation(value = "delete timing scheduler( business timings) details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/partner/business/timing/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteBusinessTiming(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) throws Exception {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.deleteBusinessTiming(partnerAddressDTO, new ResponseDTO());
    }

    @ApiOperation(value = "delete timing in schedule details", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/partner/schedule/timing/delete", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteTimingInSchedule(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) throws Exception {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.deleteTimingInSchedule(partnerAddressDTO, new ResponseDTO());
    }

    @ApiOperation(value = "Fetch partner address by location", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/get-partner-location-wise", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PartnerResponseDTO> fetchPartnersLocationWise(@Valid @RequestBody PartnerRequestDTO partnerRequestDTO) throws Exception {
        logger.info("inside fetchPartnersLocationWise  !!");
        return partnerService.fetchPartnerLocationWise(partnerRequestDTO);
    }


    private Partner convertToEntity(PartnerDTO partnerDTO) throws ParseException {
        Partner partner = modelMapper.map(partnerDTO, Partner.class);
        return partner;
    }
}


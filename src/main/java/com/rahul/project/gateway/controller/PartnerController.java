package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.service.PartnerService;
import com.rahul.project.gateway.utility.Translator;
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

@RESTController
public class PartnerController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Translator translator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PartnerService partnerService;

    @RequestMapping(value = "/api/add-update-partner", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerPartner(@Valid @RequestBody PartnerDTO partnerDTO) throws Exception {
        logger.info("inside registerPartner  !!");
        partnerService.registerPartner(convertToEntity(partnerDTO));
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(translator.toLocale("partner.create.success"));
        return responseDTO;
    }

    @RequestMapping(value = "/oauth2/api/user/partner/update/create", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO userCreateBusinessTimings() throws Exception {
        logger.info("inside registerPartner  !!");
        return partnerService.userUpdateCreate(new ResponseDTO());
    }

    @RequestMapping(value = "/oauth2/api/partner/address/update/create", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PartnerAddressDTO createUpdatePartnerAddress(@Valid @RequestBody PartnerAddressDTO partnerAddressDTO) {
        logger.info("inside createUpdatePartnerAddress  !!");
        return partnerService.createUpdatePartnerAddress(partnerAddressDTO, new ResponseDTO());
    }


    @RequestMapping(value = "/api/get-partner-location-wise", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PartnerResponseDTO> fetchPartnersLocationWise(@Valid @RequestBody PartnerRequestDTO partnerRequestDTO) throws Exception {
        logger.info("inside fetchPartnersLocationWise  !!");
        List<PartnerResponseDTO> partnerResponseDTOList = partnerService.fetchPartnerLocationWise(partnerRequestDTO);
        return partnerResponseDTOList;
    }


    private Partner convertToEntity(PartnerDTO partnerDTO) throws ParseException {
        Partner partner = modelMapper.map(partnerDTO, Partner.class);
        return partner;
    }
}


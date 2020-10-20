package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.PartnerServiceResponseDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.dto.ServiceDTO;
import com.rahul.project.gateway.dto.services.PartnerServiceListDTO;
import com.rahul.project.gateway.model.Services;
import com.rahul.project.gateway.service.ServicesService;
import com.rahul.project.gateway.utility.Translator;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.util.List;

@RESTController
public class ServiceController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Translator translator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ServicesService servicesService;

    @ApiOperation(value = "Addition and update of Services")
    @RequestMapping(value = "/api/add-update-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO addUpdateService(@Valid @RequestBody ServiceDTO serviceDTO) throws Exception {
        logger.info("inside addUpdateService  !!");
        servicesService.addUpdateService(convertToEntity(serviceDTO));
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(translator.toLocale("service.create.success"));
        return responseDTO;
    }

    @ApiOperation(value = "Addition and updation of Partner-services")
    @RequestMapping(value = "/api/create-partner-services", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO createPartnerService(@Valid @RequestBody PartnerServiceListDTO partnerServiceListDTO) throws Exception {
        logger.info("inside createPartnerService!!");
        servicesService.addUpdatePartnerService(partnerServiceListDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(translator.toLocale("partner.service.create.success"));
        return responseDTO;
    }

    @ApiOperation(value = "To fetch Partner service by partner id", response = PartnerServiceResponseDTO.class)
    @RequestMapping(value = "/api/get-partner-services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PartnerServiceResponseDTO> getPartnerServicesByPartnerId(@RequestParam Long partnerId) throws Exception {
        logger.info("inside getPartnerServicesByPartnerId!!");
        return servicesService.getPartnerServices(partnerId);
    }

    @ApiOperation(value = "To fetch services by service type id", response = ServiceDTO.class)
    @RequestMapping(value = "/api/get-services-by-service-type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ServiceDTO> getServicesByServiceType(@RequestParam Long serviceTypeId) throws Exception {
        logger.info("inside getServicesByServiceType!!");
        return servicesService.getServicesByServiceType(serviceTypeId);
    }

    @ApiOperation(value = "To fetch services by service type id", response = ServiceDTO.class)
    @RequestMapping(value = "/api/delete-service-by-service-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteServiceByServiceId(@RequestParam Long serviceId) throws Exception {
        logger.info("inside getServicesByServiceType!!");
        servicesService.deleteServiceById(serviceId);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseMessage(translator.toLocale("service.delete.success"));
        return responseDTO;
    }


    private Services convertToEntity(ServiceDTO serviceDTO) throws ParseException {
        Services services = modelMapper.map(serviceDTO, Services.class);
        return services;
    }
}

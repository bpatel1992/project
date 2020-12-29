package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.DocumentDTO;
import com.rahul.project.gateway.dto.ECardDTO;
import com.rahul.project.gateway.dto.EnquiryDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.service.PartnerService;
import com.rahul.project.gateway.utility.Translator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RESTController
public class ECardController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    Translator translator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PartnerService partnerService;

    @ApiOperation(value = "Fetch e-card details of the user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/e/card/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ECardDTO registerPartner(@ApiParam(name = "userName", value = "userName of the customer", required = true) String userName,
                                    @ApiParam(name = "partnerUserName", value = "userName of the partner", required = false) String partnerUserName)
            throws Exception {
        logger.info(" inside e-card details");
        return partnerService.getECardDetails(userName, partnerUserName);
    }

    @ApiOperation(value = "Fetch partner detail by username", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/partner/user/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ECardDTO getDetailsUser(@ApiParam(name = "userName", value = "userName of the partner", required = true) String userName) throws Exception {
        logger.info(" inside e-card details");
        return partnerService.getUserDetails(userName);
    }

    @ApiOperation(value = "Save enquiry detail for partner", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/enquiry/partner", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerPartner(@Valid @RequestBody EnquiryDTO enquiryDTO) throws Exception {
        logger.info(" inside create enquiry for partner");
        partnerService.saveEnquiry(enquiryDTO);
        return new ResponseDTO("0000", translator.toLocale("enquiry.create.success"));
    }

    @ApiOperation(value = "Fetch e-card assets of partner", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/api/assets/partner/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssets(@ApiParam(name = "userName", value = "userName of the partner", required = true) String userName,
                                        @ApiParam(name = "fileName", value = "fileName of the partner", required = false) String fileName,
                                        @ApiParam(name = "asset", value = "service name of the partner", required = true) String asset) throws Exception {
        return partnerService.getECardAssets(userName, asset, fileName);
    }

    @ApiOperation(value = "Fetch e-card assets of user by key", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/user/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUser(@ApiParam(name = "key", value = "key value to fetch assets of user", required = true) String key) throws Exception {
        return partnerService.getECardAssetsUser(key);
    }

    @ApiOperation(value = "Fetch e-card assets of partner by key", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/partner/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsPartner(@ApiParam(name = "key", value = "key value to fetch assets of partner", required = true) String key) throws Exception {
        return partnerService.getECardAssetsPartner(key);
    }

    @ApiOperation(value = "Delete e-card assets of partner by key", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})
    @DeleteMapping(value = {"/oauth2/api/assets/delete/partner/e/card"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteAssetsPartner(@ApiParam(name = "key", value = "key value to fetch assets of partner", required = true) String key) throws Exception {
        return partnerService.deleteECardAssetsPartner(key, new ResponseDTO());
    }

    @ApiOperation(value = "Delete e-card assets of user by key", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})
    @DeleteMapping(value = {"/oauth2/api/assets/delete/user/e/card"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteAssetsUser(@ApiParam(name = "key", value = "key value to fetch assets of user", required = true) String key) throws Exception {
        return partnerService.deleteECardAssetsUser(key, new ResponseDTO());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "file", value = "document to be uploaded against"
                    , required = true, dataType = "String", paramType = "request"),
            @ApiImplicitParam(name = "id", value = "Partner document Id"
                    , required = true, dataType = "String", paramType = "request"),
            @ApiImplicitParam(name = "partnerId", value = "Partner Id"
                    , required = true, dataType = "String", paramType = "request")})
    @PostMapping(value = "/oauth2/api/upload/partner/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO uploadDoc(@RequestParam("file") MultipartFile file, @RequestParam("id") Long docId
            , @RequestParam("partnerId") Long partnerId) throws Exception {
        return partnerService.uploadDoc(file, docId, partnerId);
    }
}


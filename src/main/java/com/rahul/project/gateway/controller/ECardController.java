package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.DocumentDTO;
import com.rahul.project.gateway.dto.ECardDTO;
import com.rahul.project.gateway.dto.EnquiryDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.service.PartnerService;
import com.rahul.project.gateway.utility.Translator;
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

    @RequestMapping(value = "/api/e/card/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ECardDTO registerPartner(@RequestParam("userName") String userName,
                                    @RequestParam(value = "partnerUserName", required = false) String partnerUserName) throws Exception {
        logger.info(" inside e-card details");
        return partnerService.getECardDetails(userName, partnerUserName);
    }

    @RequestMapping(value = "/api/partner/user/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ECardDTO getDetailsUser(@RequestParam("userName") String userName) throws Exception {
        logger.info(" inside e-card details");
        return partnerService.getUserDetails(userName);
    }

    @RequestMapping(value = "/api/enquiry/partner", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO registerPartner(@Valid @RequestBody EnquiryDTO enquiryDTO) throws Exception {
        logger.info(" inside create enquiry for partner");
        partnerService.saveEnquiry(enquiryDTO);
        return new ResponseDTO("0000", translator.toLocale("enquiry.create.success"));
    }

    @GetMapping(value = {"/api/assets/partner/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssets(@RequestParam("userName") String userName,
                                        @RequestParam(value = "fileName", required = false) String fileName,
                                        @RequestParam("asset") String asset) throws Exception {
        return partnerService.getECardAssets(userName, asset, fileName);
    }

    @GetMapping(value = {"/assets/user/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUser(@RequestParam("key") String key) throws Exception {
        return partnerService.getECardAssetsUser(key);
    }

    @GetMapping(value = {"/assets/partner/e/card"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsPartner(@RequestParam("key") String key) throws Exception {
        return partnerService.getECardAssetsPartner(key);
    }

    @DeleteMapping(value = {"/oauth2/api/assets/delete/partner/e/card"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteAssetsPartner(@RequestParam("key") String key) throws Exception {
        return partnerService.deleteECardAssetsPartner(key, new ResponseDTO());
    }

    @DeleteMapping(value = {"/oauth2/api/assets/delete/user/e/card"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteAssetsUser(@RequestParam("key") String key) throws Exception {
        return partnerService.deleteECardAssetsUser(key, new ResponseDTO());
    }

    @PostMapping(value = "/oauth2/api/upload/partner/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDTO uploadDoc(@RequestParam("file") MultipartFile file, @RequestParam("id") Long docId
            , @RequestParam("partnerId") Long partnerId) throws Exception {
        return partnerService.uploadDoc(file, docId, partnerId);
    }
}


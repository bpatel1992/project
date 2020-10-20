package com.rahul.project.gateway.controller;

import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.model.PetBreed;
import com.rahul.project.gateway.service.FileHandlingService;
import com.rahul.project.gateway.service.GoogleCloudService;
import com.rahul.project.gateway.service.PetService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Api(value = "Guidelines")
@RESTController
@TransactionalService
@RequestMapping({"/oauth2/api", "/api"})
public class GoogleCloudController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    FileHandlingService fileHandlingService;
    @Autowired
    GoogleCloudService googleCloudService;
    @Autowired
    CommonUtility commonUtility;
    @Autowired
    PetService petService;

    @RequestMapping(value = "/breed-identification", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Make a POST request to upload the file and get breed related information",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The POST call is Successful"),
            @ApiResponse(code = 500, message = "The POST call is Failed"),
            @ApiResponse(code = 404, message = "The API could not be found")
    })
    public List<PetBreed> uploadPicGetDetails(@RequestParam("file") MultipartFile file) throws Exception {
        String userId = String.valueOf(commonUtility.getLoggedInUser());
        logger.info("profile pic service userId===" + userId);
        String path = fileHandlingService.uploadBreedIdentificationFile(file);
        AnnotateImageResponse annotateImageResponse = googleCloudService.detectFaces(Paths.get(path), 100);
        List<String> labels = annotateImageResponse.getLabelAnnotations().stream()
                .map(EntityAnnotation::getDescription).collect(Collectors.toList());
        return petService.getPetData(labels);

    }

    @RequestMapping(value = "/getPetBreeds", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Make a POST request to upload the file",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The POST call is Successful"),
            @ApiResponse(code = 500, message = "The POST call is Failed"),
            @ApiResponse(code = 404, message = "The API could not be found")
    })
    public List<PetBreed> getPetList(@Valid @PathVariable("documentId") String documentId,
                                     @RequestParam("file") MultipartFile file) throws Exception {
        return petService.getPetBreeds();
    }
}

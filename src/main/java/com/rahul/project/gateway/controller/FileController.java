package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.service.FileHandlingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Fetch symptom checker file by file name, " + "\n" +
                "2. Fetch assets file by file name, " + "\n" +
                "3. Fetch assets file by random key of user, " + "\n" +
                "4. Fetch avatar file by random key of user, " + "\n" +
                "5. Fetch cover file by random key of user, " + "\n" +
                "6. Fetch user certificates file by random key of user and file name, " + "\n" +
                "7. Fetch user gallery files by random key of user and file name, " + "\n" +
                "8. Fetch user pet assets files by random key of user", tags = {"File based services"})
public class FileController {

    private final
    FileHandlingService fileHandlingService;

    @Autowired
    public FileController(FileHandlingService fileHandlingService) {
        this.fileHandlingService = fileHandlingService;
    }

    @ApiOperation(value = "Create a symptom checker file", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/oauth2/api/sc/file/list")
    public List<ResponseDTO> symptomCheckerFileList() throws Exception {
        return fileHandlingService.symptomCheckerFileList();
    }

    @ApiOperation(value = "fetch symptom checker file by file name", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/api/sc/file/fetch", "/sc/file/fetch"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getImage(@ApiParam(name = "fileName", required = true) String fileName) {
        return fileHandlingService.getSymptomCheckerFile(fileName);
    }

    @ApiOperation(value = "Create a symptom checker file", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/oauth2/api/sc/file/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO saveSymptomCheckerFile(@ApiParam(name = "file", required = true) MultipartFile file,
                                              @ApiParam(name = "name", required = true) String name) throws Exception {
        return fileHandlingService.saveSymptomCheckerFile(file, name);
    }

    @ApiOperation(value = "Insert a symptom checker file", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/oauth2/api/sc/file/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO putSymptomCheckerFile(@ApiParam(name = "file", required = true) MultipartFile file,
                                             @ApiParam(name = "name", required = true) String name) throws Exception {
        return fileHandlingService.putSymptomCheckerFile(file, name);
    }

    @ApiOperation(value = "Delete a symptom checker file", produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "/oauth2/api/sc/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO deleteSymptomCheckerFile(@ApiParam(name = "name", required = true) String name) throws Exception {
        return fileHandlingService.deleteSymptomCheckerFile(name);
    }

    @ApiOperation(value = "fetch assets file by file name", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/api/assets/file/fetch", "/assets/file/fetch"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssets(@ApiParam(name = "fileName", required = true) String fileName) {
        return fileHandlingService.getAssets(fileName);
    }

    @ApiOperation(value = "fetch assets file by random key of user", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/api/assets/user/profile", "/assets/user/profile"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUser(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey) {
        return fileHandlingService.getAssetsUser(randomKey);
    }

    @ApiOperation(value = "fetch avatar file by random key of user", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/user/avatar"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserAvatar(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey) {
        return fileHandlingService.getAssetsUserAvatar(randomKey);
    }

    @ApiOperation(value = "fetch cover file by random key of user", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/user/cover"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserCover(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey) {
        return fileHandlingService.getAssetsUserCover(randomKey);
    }

    @ApiOperation(value = "fetch user certificates file by random key of user and file name", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/user/certificate"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserCertificate(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey,
                                                       @ApiParam(name = "fileName", required = true) String fileName) throws Exception {
        return fileHandlingService.getAssetsUserCertificate(randomKey, fileName);
    }

    @ApiOperation(value = "fetch user gallery files by random key of user and file name", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/user/gallery"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserGallery(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey,
                                                   @ApiParam(name = "fileName", required = true) String fileName) throws Exception {
        return fileHandlingService.getAssetsUserGallery(randomKey, fileName);
    }

    @ApiOperation(value = "fetch user pet assets files by random key of user", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @GetMapping(value = {"/assets/pet/profile"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsPet(@ApiParam(name = "randomKey", value = "random key assigned to user", required = true) String randomKey) {
        return fileHandlingService.getAssetsPet(randomKey);
    }
}

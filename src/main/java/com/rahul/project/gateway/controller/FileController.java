package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.service.FileHandlingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "Used for file upload and fetching")
@RESTController
public class FileController {

    private final
    FileHandlingService fileHandlingService;

    @Autowired
    public FileController(FileHandlingService fileHandlingService) {
        this.fileHandlingService = fileHandlingService;
    }

    @GetMapping(value = {"/api/sc/file/fetch", "/sc/file/fetch"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getImage(@RequestParam("fileName") String fileName) {
        return fileHandlingService.getSymptomCheckerFile(fileName);
    }

    @GetMapping(value = {"/api/assets/file/fetch", "/assets/file/fetch"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssets(@RequestParam("fileName") String fileName) {
        return fileHandlingService.getAssets(fileName);
    }

    @GetMapping(value = {"/api/assets/user/profile", "/assets/user/profile"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUser(@RequestParam("randomKey") String randomKey) {
        return fileHandlingService.getAssetsUser(randomKey);
    }

    @GetMapping(value = {"/assets/user/avatar"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserAvatar(@RequestParam("randomKey") String randomKey) {
        return fileHandlingService.getAssetsUserAvatar(randomKey);
    }

    @GetMapping(value = {"/assets/user/cover"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserCover(@RequestParam("randomKey") String randomKey) {
        return fileHandlingService.getAssetsUserCover(randomKey);
    }

    @GetMapping(value = {"/assets/user/certificate"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserCertificate(@RequestParam("randomKey") String randomKey,
                                                       @RequestParam("fileName") String fileName) throws Exception {
        return fileHandlingService.getAssetsUserCertificate(randomKey, fileName);
    }

    @GetMapping(value = {"/assets/user/gallery"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsUserGallery(@RequestParam("randomKey") String randomKey,
                                                   @RequestParam("fileName") String fileName) throws Exception {
        return fileHandlingService.getAssetsUserGallery(randomKey, fileName);
    }

    @GetMapping(value = {"/assets/pet/profile"}, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource getAssetsPet(@RequestParam("randomKey") String randomKey) {
        return fileHandlingService.getAssetsPet(randomKey);
    }
}

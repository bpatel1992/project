package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.service.VideoStreamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Stream video file by filetype and file name",tags = { "Video stream services" })
@RequestMapping({"/oauth2/api", "/api"})
public class VideoStreamController {

    @Autowired
    private VideoStreamService videoStreamService;

    @ApiOperation(value = "Stream video file by filetype and file name")
    @GetMapping("/stream/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {
        return Mono.just(videoStreamService.prepareContent(fileName, fileType, httpRangeList));
    }


}

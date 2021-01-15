package com.rahul.project.gateway.video.controller;


import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.video.dto.VideoMeetingRequestDTO;
import com.rahul.project.gateway.video.model.VideoAppointmentMapping;
import com.rahul.project.gateway.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Fetch user specific meeting redirect url, " + "\n" +
                "2. Join meeting by user specific key", tags = {"Video services"})
public class VideoServiceController {

    private VideoService videoService;

    @Value("${video.service.redirectUrl}")
    private String redirectUrl;

    @Value("${video.service.meetingUrl}")
    private String meetingUrl;

    @Autowired
    public VideoServiceController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "fetch channel details by senderId and receiverId", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    @RequestMapping(value = "/oauth2/api/fetchMeetingUrl")
    public @ResponseBody
    String fetchMeetingUrl(@RequestBody VideoMeetingRequestDTO videoMeetingRequestDTO)
            throws BusinessException {
        String joiningKey = videoService.fetchVideoAppointmentMapping(videoMeetingRequestDTO);
        return meetingUrl + "/" + joiningKey;
    }

    @ApiOperation(value = "fetch channel details by senderId and receiverId", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    @RequestMapping(value = "/api/join/{joiningKey}")
    public ModelAndView joinMeeting(@PathVariable(value = "joiningKey") String joiningKey, RedirectAttributes redirectAttributes)
            throws BusinessException {
        VideoAppointmentMapping videoAppointmentMapping = videoService.fetchVideoAppointmentMappingByJoiningKey(joiningKey);
        redirectAttributes.addAttribute("sessionId", videoAppointmentMapping.getSessionId());
        redirectAttributes.addAttribute("token", videoAppointmentMapping.getToken());
        return new ModelAndView("redirect:" + redirectUrl);
    }


//    @Scheduled(cron = "* */15 * * * *")
//    public void performTaskUsingCron() {
//        videoService.generateTokenForNewAppointments();
//    }


}
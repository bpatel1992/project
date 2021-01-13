package com.rahul.project.gateway.video.controller;


import com.rahul.project.gateway.chat.exception.IsSameUserException;
import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.video.model.VideoAppointmentMapping;
import com.rahul.project.gateway.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Fetch channel details by senderId and receiverId, " + "\n" +
                "2. Fetch chat message by channelId, pageNo and pageSize",tags = { "Chat services" })
public class VideoServiceController {

    private VideoService videoService;

    @Value("${video.service.redirectUrl}")
    private String redirectUrl;

    @Autowired
    public VideoServiceController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "fetch channel details by senderId and receiverId", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    @RequestMapping(value = "/api/joinMeeting")
    public ModelAndView joinMeeting(@RequestParam(value = "appointmentId") Long appointmentId, RedirectAttributes redirectAttributes)
            throws IsSameUserException, BusinessException {
        VideoAppointmentMapping videoAppointmentMapping = videoService.fetchVideoAppointmentMapping(appointmentId);
        redirectAttributes.addAttribute("sessionId", videoAppointmentMapping.getSessionId());
        redirectAttributes.addAttribute("token", videoAppointmentMapping.getToken());
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @Scheduled(cron = "* */15 * * * *")
    public void performTaskUsingCron() {
        videoService.generateTokenForNewAppointments();
    }


}
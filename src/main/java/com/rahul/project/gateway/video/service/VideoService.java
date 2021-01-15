package com.rahul.project.gateway.video.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.dto.webclient.WebRequest;
import com.rahul.project.gateway.model.Appointment;
import com.rahul.project.gateway.model.StatusType;
import com.rahul.project.gateway.repository.AppointmentRepository;
import com.rahul.project.gateway.repository.StatusTypeRepository;
import com.rahul.project.gateway.utility.WebClientServiceHelper;
import com.rahul.project.gateway.video.dto.VideoMeetingRequestDTO;
import com.rahul.project.gateway.video.model.VideoAppointmentMapping;
import com.rahul.project.gateway.video.model.VideoChannel;
import com.rahul.project.gateway.video.repository.VideoAppointmentMappingRepository;
import com.rahul.project.gateway.video.repository.VideoChannelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class VideoService {
    private VideoAppointmentMappingRepository videoAppointmentMappingRepository;

    private VideoChannelRepository videoChannelRepository;

    private AppointmentRepository appointmentRepository;

    private WebClientServiceHelper webClient;

    private StatusTypeRepository statusTypeRepository;

    @Value("${video.service.baseurl}")
    private String baseurl;

    @Value("${video.service.session.api}")
    private String sessionApi;

    @Value("${video.service.token.api}")
    private String tokenApi;

    @Autowired
    public VideoService(
            VideoAppointmentMappingRepository videoAppointmentMappingRepository,
            VideoChannelRepository videoChannelRepository,
            AppointmentRepository appointmentRepository,
            WebClientServiceHelper webClient,
            StatusTypeRepository statusTypeRepository) {
        this.videoAppointmentMappingRepository = videoAppointmentMappingRepository;
        this.videoChannelRepository = videoChannelRepository;
        this.appointmentRepository = appointmentRepository;
        this.webClient = webClient;
        this.statusTypeRepository = statusTypeRepository;
    }

    public String fetchVideoAppointmentMapping(VideoMeetingRequestDTO videoMeetingRequestDTO) throws BusinessException {
        VideoAppointmentMapping videoAppointmentMapping = videoAppointmentMappingRepository.findByAppointmentId(videoMeetingRequestDTO.getAppointmentId());
        if (Objects.isNull(videoAppointmentMapping)) {
            videoAppointmentMapping = generateTokenForGivenAppointment(videoMeetingRequestDTO.getAppointmentId());
            if (Objects.isNull(videoAppointmentMapping)) {
                throw new BusinessException("Error occurred while joining meeting");
            }
        }
        String joiningKey = null;
        if (Objects.nonNull(videoMeetingRequestDTO.getUserType())
                && StringUtils.equalsIgnoreCase(videoMeetingRequestDTO.getUserType(), "partner")) {
            joiningKey = videoAppointmentMapping.getPartnerKey();
        } else {
            joiningKey = videoAppointmentMapping.getUserKey();
        }
        return joiningKey;
    }

    public VideoAppointmentMapping fetchVideoAppointmentMappingByJoiningKey(String joiningKey) throws BusinessException {
        VideoAppointmentMapping videoAppointmentMapping = videoAppointmentMappingRepository.findByUserKeyOrPartnerKey(joiningKey);
        if (Objects.isNull(videoAppointmentMapping)) {
            throw new BusinessException("Error occurred while joining meeting");
        }
        return videoAppointmentMapping;
    }


    /*public void generateTokenForNewAppointments() {
        List<StatusType> statusTypeList =
                statusTypeRepository.findByStatusTypeNameIn(newArrayList(Appointment.AppointmentStatusType.ACTIVE.name(),
                        Appointment.AppointmentStatusType.SCHEDULED.name()));
        List<Long> approvedAppointmentStatus = statusTypeList.stream().map(StatusType::getId).collect(Collectors.toList());
        List<Appointment> appointmentList =
                appointmentRepository.findAppointmentsByAppointmentTypeAndStatus("Online Consultation", Boolean.FALSE,
                        approvedAppointmentStatus);

        appointmentList.forEach(appointment -> {
            buildAndSaveVideoAppointmentMapping(appointment);
        });

    }*/

    public VideoAppointmentMapping generateTokenForGivenAppointment(Long appointmentId) {
        List<StatusType> statusTypeList =
                statusTypeRepository.findByStatusTypeNameIn(newArrayList(Appointment.AppointmentStatusType.ACTIVE.name(),
                        Appointment.AppointmentStatusType.SCHEDULED.name()));
        List<Long> approvedAppointmentStatus = statusTypeList.stream().map(StatusType::getId).collect(Collectors.toList());
        Appointment appointment =
                appointmentRepository.findByOnlineConsultationAppointmentId("Online Consultation", appointmentId, Boolean.FALSE,
                        approvedAppointmentStatus);
        VideoAppointmentMapping videoAppointmentMapping = null;
        if (Objects.nonNull(appointment)) {
            videoAppointmentMapping = buildAndSaveVideoAppointmentMapping(appointment);
        }
        return videoAppointmentMapping;
    }

    public VideoAppointmentMapping buildAndSaveVideoAppointmentMapping(Appointment appointment) {
        VideoChannel videoChannel =
                videoChannelRepository.findByPartnerIdAndUserId(appointment.getAttendant().getId(), appointment.getCustomer().getId());
        Long expiryInMins = calculateExpiryTimeInMinutes(appointment);
        String token = null;
        String sessionId = null;
        if (Objects.isNull(videoChannel)) {
            sessionId = fetchSessionId().block();
            videoChannel = buildAndSaveVideoChannel(appointment, sessionId);
        }
        token = fetchToken(expiryInMins, videoChannel.getSessionId()).block();
        VideoAppointmentMapping videoAppointmentMapping =
                videoAppointmentMappingRepository.findByAppointmentId(appointment.getId());
        if (Objects.isNull(videoAppointmentMapping)) {
            videoAppointmentMapping = buildAndSaveVideoAppointmentMapping(appointment, videoChannel.getSessionId(), token, expiryInMins);
        }
        if (Objects.nonNull(token)) {
            appointment.setVideoTokenGenerated(Boolean.TRUE);
            appointmentRepository.save(appointment);
        }
        return videoAppointmentMapping;
    }

    private Mono<String> fetchSessionId() {
        WebRequest request = WebRequest.builder().apiUrl(baseurl + sessionApi).payload("").build();
        Mono<String> sessionIdMono = this.webClient.buildPostRequest(request, String.class);
        return sessionIdMono;
    }

    private Mono<String> fetchToken(Long expiryTimeInMins, String sessionId) {
        WebRequest request = WebRequest.builder().apiUrl(baseurl + tokenApi).build();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("expiryTime", expiryTimeInMins);
        jsonObject.addProperty("sessionId", sessionId);
        String jsonRequest = new Gson().toJson(jsonObject);
        request.setPayload(jsonRequest);
        Mono<String> tokenMono = this.webClient.buildPostRequest(request, String.class);
        return tokenMono;
    }

    private Long calculateExpiryTimeInMinutes(Appointment appointment) {
        Date date = appointment.getAppointmentDate();
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.set(Calendar.HOUR_OF_DAY, appointment.getAppointmentToTime().getHours());
        time.set(Calendar.MINUTE, appointment.getAppointmentToTime().getMinutes());
        LocalDateTime expiryDateTime = LocalDateTime.ofInstant(time.toInstant(), time.getTimeZone().toZoneId());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from(now);
        long minutes = tempDateTime.until(expiryDateTime, ChronoUnit.MINUTES) + 5;
        return minutes;
    }

    private VideoChannel buildAndSaveVideoChannel(Appointment appointment, String sessionId) {
        VideoChannel videoChannel = new VideoChannel();
        videoChannel.setSessionId(sessionId);
        videoChannel.setPartnerId(appointment.getAttendant().getId());
        videoChannel.setUserId(appointment.getCustomer().getId());
        return videoChannelRepository.save(videoChannel);
    }

    private VideoAppointmentMapping buildAndSaveVideoAppointmentMapping(Appointment appointment, String sessionId, String token, Long expiryInMin) {
        VideoAppointmentMapping videoAppointmentMapping = new VideoAppointmentMapping();
        videoAppointmentMapping.setSessionId(sessionId);
        videoAppointmentMapping.setToken(token);
        videoAppointmentMapping.setAppointmentId(appointment.getId());
        videoAppointmentMapping.setAppointmentFromTime(appointment.getAppointmentFromTime());
        videoAppointmentMapping.setExpiryInMin(expiryInMin);
        videoAppointmentMapping.setUserKey(UUID.randomUUID().toString());
        videoAppointmentMapping.setPartnerKey(UUID.randomUUID().toString());
        return videoAppointmentMappingRepository.save(videoAppointmentMapping);
    }

}
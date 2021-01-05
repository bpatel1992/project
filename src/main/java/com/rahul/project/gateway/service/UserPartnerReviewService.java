package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.controller.CrudCtrlBase;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.dto.userreview.UserPartnerReviewDTO;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.Pet;
import com.rahul.project.gateway.repository.AppointmentReasonRepository;
import com.rahul.project.gateway.repository.AppointmentTypeRepository;
import com.rahul.project.gateway.repository.UserPartnerReviewRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

@TransactionalService
public class UserPartnerReviewService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    private Environment environment;

    @Autowired
    private UserPartnerReviewRepository userPartnerReviewRepository;

    @Autowired
    private AppointmentReasonRepository appointmentReasonRepository;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;


    public boolean saveReview(UserPartnerReviewDTO userPartnerReviewDTO) throws BusinessException {
        UserPartnerReview userPartnerReview = new UserPartnerReview();
        BeanUtils.copyProperties(userPartnerReviewDTO, userPartnerReview);
        Optional<AppointmentReason> appointmentReasonOptional = appointmentReasonRepository.findById(userPartnerReviewDTO.getAppointmentReasonId());

        Optional<AppointmentType> appointmentTypeOptional = appointmentTypeRepository.findById(userPartnerReviewDTO.getAppointmentTypeId());

        if (appointmentReasonOptional.isPresent() && appointmentTypeOptional.isPresent()) {
            userPartnerReview.setAppointmentReason(appointmentReasonOptional.get());
            userPartnerReview.setAppointmentType(appointmentTypeOptional.get());
        } else
            throw new BusinessException("AppointmentReason or appointmentType not found!");

        userPartnerReview.setCreated(new Date());
        userPartnerReview.setStatus("PENDING");
        return userPartnerReviewRepository.save(userPartnerReview) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    public List<UserPartnerReviewDTO> fetchReviewsByUser(UserPartnerReviewDTO userPartnerReviewDTO) throws Exception {
        List<UserPartnerReview> userPartnerReviewList =
                userPartnerReviewRepository.findByPartnerIdAndStatusOrderByCreatedAsc(userPartnerReviewDTO.getPartnerId(),
                        userPartnerReviewDTO.getStatus(),
                        PageRequest.of(userPartnerReviewDTO.getPageRequestDTO().getPageNo(),
                                userPartnerReviewDTO.getPageRequestDTO().getPageSize()));
        List<UserPartnerReviewDTO> userPartnerReviewDTOList = userPartnerReviewList.stream().map(
                userPartnerReview -> {
                    UserPartnerReviewDTO userPartnerReviewDTO1 = new UserPartnerReviewDTO();
                    BeanUtils.copyProperties(userPartnerReview, userPartnerReviewDTO1);
                    userPartnerReviewDTO1.setAppointmentReason(userPartnerReview.getAppointmentReason().getAppointmentReason());
                    userPartnerReviewDTO1.setAppointmentType(userPartnerReview.getAppointmentType().getAppointmentType());
                    userPartnerReviewDTO1.setAppointmentReasonId(userPartnerReview.getAppointmentReason().getId());
                    userPartnerReviewDTO1.setAppointmentTypeId(userPartnerReview.getAppointmentType().getId());
                    return userPartnerReviewDTO1;
                }).collect(Collectors.toList());
        return userPartnerReviewDTOList;
    }

    public boolean updateReviewStatus(UserPartnerReviewDTO userPartnerReviewDTO) throws BusinessException {
        Optional<UserPartnerReview> userPartnerReviewOptional = userPartnerReviewRepository.findById(userPartnerReviewDTO.getId());
        UserPartnerReview userPartnerReview = null;
        if (userPartnerReviewOptional.isPresent()) {
            userPartnerReview = userPartnerReviewOptional.get();
            String status = StringUtils.equalsAnyIgnoreCase(userPartnerReviewDTO.getStatus(),"approved") ? "APPROVED" : "REJECTED";
            userPartnerReview.setStatus(status);
        } else
            throw new BusinessException("Review not found by given review Id!");
        return userPartnerReviewRepository.save(userPartnerReview) != null ? Boolean.TRUE : Boolean.FALSE;
    }





}

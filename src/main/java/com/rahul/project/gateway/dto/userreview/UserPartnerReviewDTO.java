package com.rahul.project.gateway.dto.userreview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.chat.dto.PageRequestDTO;
import com.rahul.project.gateway.dto.UserRelationDTO;
import com.rahul.project.gateway.model.AppointmentReason;
import com.rahul.project.gateway.model.AppointmentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPartnerReviewDTO {

    private Long id;

    @NonNull
    private Long userId;

    @NonNull
    private String userName;

    @NonNull
    private Long partnerId;

    @NonNull
    private Long appointmentId;

    @NonNull
    private Long appointmentTypeId;

    @NonNull
    private Long appointmentReasonId;

    private String comment;

    private int rating;

    private Date created;

    private String status;

    private String appointmentType;

    private String appointmentReason;

    private PageRequestDTO pageRequestDTO;

}
package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "user_partner_review")
public class UserPartnerReview implements Serializable {

    @Id
    @Basic
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "user_name")
    private String userName;

    @Basic
    @Column(name = "partner_id")
    private Long partnerId;

    @Basic
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id")
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "appointment_reason_id")
    private AppointmentReason appointmentReason;

    @Basic
    @Column(name = "comment")
    private String comment;

    @Min(1)
    @Max(5)
    @Basic
    @Column(name = "rating")
    private int rating;

    @Column(name = "created", nullable = false)
    private Date created;

    @Basic
    @Column(name = "status")
    private String status;

    public UserPartnerReview() {
    }

    public UserPartnerReview(Long id, Long userId, String userName, Long partnerId, Long appointmentId, AppointmentType appointmentType, AppointmentReason appointmentReason, String comment, int rating, Date created) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.partnerId = partnerId;
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;
        this.appointmentReason = appointmentReason;
        this.comment = comment;
        this.rating = rating;
        this.created = new Date();
    }

}
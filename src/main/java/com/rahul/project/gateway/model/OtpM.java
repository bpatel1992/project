package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Data
@Entity
@Table(name = "otp_m")
public class OtpM implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "generation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar generationTime;

    @Column(name = "expiration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expirationTime;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "mobile_country_id", referencedColumnName = "id")
    private Country country;

    @Basic
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "status")
    private String status;
}

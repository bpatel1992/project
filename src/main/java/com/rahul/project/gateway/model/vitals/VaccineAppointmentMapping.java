package com.rahul.project.gateway.model.vitals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.model.LocalizedVitalService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User Role Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "vaccine_appointment_m")
public class VaccineAppointmentMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "appointment_Id")
    private Long appointmentId;

    @NotNull
    @Column(name = "pet_Id")
    private Long petId;

    @NotNull
    @Column(name = "vaccine_Id")
    private Long vaccineId;

    @NotNull
    @Column(name = "vaccine_name")
    private String vaccineName;

    @Basic
    @Column(name = "next_appointment_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date nextAppointmentDate;

    @Basic
    @CreatedDate
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @NotNull
    @Column(name = "created_by")
    private String createdBy;

    @Basic
    @LastModifiedDate
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modificationDate;

    @NotNull
    @Column(name = "modified_by")
    private String modifiedBy;

}

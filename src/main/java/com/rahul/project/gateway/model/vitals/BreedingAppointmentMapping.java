package com.rahul.project.gateway.model.vitals;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User Role Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "breeding_appointment_m")
public class BreedingAppointmentMapping implements Serializable {

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

    @Basic
    @Column(name = "date_of_heat")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateOfHeat;

    @Basic
    @Column(name = "date_of_whelping")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateOfWhelping;

    @NotNull
    @Column(name = "number_of_babies_born")
    private Integer noOfBabiesBorn;

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

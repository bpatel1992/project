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
@Table(name = "grooming_appointment_m")
public class GroomingAppointmentMapping implements Serializable {

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
    @Column(name = "nail_clipping", columnDefinition = "boolean default true", nullable = false)
    private boolean nailClipping;

    @NotNull
    @Column(name = "medicated_bath", columnDefinition = "boolean default true", nullable = false)
    private boolean medicatedBath;

    @NotNull
    @Column(name = "ear_cleaning", columnDefinition = "boolean default true", nullable = false)
    private boolean earCleaning;

    @NotNull
    @Column(name = "hair_clipping", columnDefinition = "boolean default true", nullable = false)
    private boolean hairClipping;

    @Column(name = "other_services")
    private String otherServices;

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

    @JsonIgnore
    @OneToMany(mappedBy = "departmentEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedVitalService> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

}

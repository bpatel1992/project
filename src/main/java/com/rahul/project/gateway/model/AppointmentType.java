package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "appointment_type_m")
public class AppointmentType implements Serializable {

    @Id
    @SequenceGenerator(name = "AppointmentType_gen", allocationSize = 1, sequenceName = "AppointmentType_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AppointmentType_gen")
    private Long id;

    @Column(name = "appointment_type")
    private String appointmentType;

    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "appointmentTypeEntity",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedAppointmentType> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }
}

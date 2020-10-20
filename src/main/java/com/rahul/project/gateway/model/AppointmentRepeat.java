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
@Table(name = "appointment_repeat_m")
public class AppointmentRepeat implements Serializable {

    @Id
    @SequenceGenerator(name = "appointment_repeat_gen", allocationSize = 1, sequenceName = "appointment_repeat_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_repeat_gen")
    private Long id;

    @Column(name = "appointment_repeat")
    private String appointmentRepeat;

    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "appointmentRepeatEntity",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedAppointmentRepeat> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }
}

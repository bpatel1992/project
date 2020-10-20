package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rahul malhotra
 * date 2020-09-14
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reminder_type")
public class ReminderType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "reminderTypeEntity",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedReminderType> localizations = new HashMap<>();
    @JsonInclude()
    @Transient
    private String label;
    @Basic
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;
    @Basic
    @Column(name = "reminder_type")
    private String reminderType;

    public ReminderType(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }

}

package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "possible_cause_priority_m")
public class PossibleCausePriority implements Serializable {

    @Id
    @SequenceGenerator(name = "possible_cause_priority_gen", allocationSize = 1, sequenceName = "possible_cause_priority_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "possible_cause_priority_gen")
    private Long id;

    @Column(name = "tip")
    private String tip;

    @JsonInclude()
    @Transient
    private String priority;

    @JsonInclude()
    @Transient
    private String description;

    @Column(name = "colour")
    private String colour;

    @JsonIgnore
    @OneToMany(mappedBy = "possibleCausePriorityEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedPossibleCausePriority> localizations = new HashMap<>();

    public String getPriority() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getPriority() : "";
    }

    public String getDescription() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getDescription() : "";
    }
}

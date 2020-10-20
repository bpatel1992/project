package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "day_m")
public class Day implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "short_code")
    private String shortCode;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "dayEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedDay> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;
    @JsonInclude()
    @Transient
    private String shortCodeLabel;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }

    public String getShortCodeLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getShortCodeLabel() : null;
    }
}

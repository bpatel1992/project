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

@Entity
@Getter
@Setter
@Table(name = "assessment_option_m")
public class AssessmentOption implements Serializable {

    @Id
    @SequenceGenerator(name = "assessment_option_gen", allocationSize = 1, sequenceName = "assessment_option_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_option_gen")
    private Long id;

    @Column(name = "assessment_option_name", length = 250)
    private String option;

    @JsonIgnore
    @OneToMany(mappedBy = "assessmentOptionEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedAssessmentOption> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

}

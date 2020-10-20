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
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "assessment_m")
public class Assessment implements Serializable {

    @Id
    @SequenceGenerator(name = "assessment_gen", allocationSize = 1, sequenceName = "assessment_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_gen")
    private Long id;

    @Column(name = "assessment_name", length = 250)
    private String assessment;

    @ManyToMany
    @JoinTable(name = "assessment_options_mp", joinColumns = @JoinColumn(name = "assessment_id")
            , inverseJoinColumns = @JoinColumn(name = "assessment_option_id"))
    private Set<AssessmentOption> assessmentOptions;

    @JsonIgnore
    @OneToMany(mappedBy = "assessmentEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedAssessment> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

}

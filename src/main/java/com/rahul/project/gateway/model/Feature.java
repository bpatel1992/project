package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

/**
 * User Role Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "feature_m")
public class Feature implements Serializable {

    @Id
    @SequenceGenerator(name = "feature_gen", allocationSize = 1, sequenceName = "feature_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feature_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "feature_name")
    private String name;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "route", length = 150)
    private String route;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "feature_description")
    private String description;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @ManyToMany
    @JsonIgnoreProperties({"features", "roles"})
    @JoinTable(name = "functionality_feature_mp", joinColumns = @JoinColumn(name = "feature_id")
            , inverseJoinColumns = @JoinColumn(name = "functionality_id"))
    private Set<Functionality> functionaries;

    @JsonIgnore
    @OneToMany(mappedBy = "featureEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedFeature> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

}

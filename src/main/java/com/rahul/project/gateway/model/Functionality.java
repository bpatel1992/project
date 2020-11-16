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
@Table(name = "functionality_m")
public class Functionality implements Serializable {

    @Id
    @SequenceGenerator(name = "functionality_gen", allocationSize = 1, sequenceName = "functionality_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "functionality_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "functionality_name")
    private String name;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "route", length = 150)
    private String route;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "functionality_description")
    private String description;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @ManyToMany
    @JsonIgnoreProperties("functionaries")
    @JoinTable(name = "functionality_feature_mp", joinColumns = @JoinColumn(name = "functionality_id")
            , inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> features;

    @JsonIgnore
    @OneToMany(mappedBy = "functionalityEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedFunctionality> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

    /*@ManyToMany
    @JsonIgnoreProperties({"functionaries", "privileges"})
    @JoinTable(name = "role_functionality_mp", joinColumns = @JoinColumn(name = "functionality_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;*/

}

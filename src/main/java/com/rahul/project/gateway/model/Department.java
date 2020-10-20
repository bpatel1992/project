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
@Table(name = "department_m")
public class Department implements Serializable {

    @Id
    @SequenceGenerator(name = "department_gen", allocationSize = 1, sequenceName = "department_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "department_name")
    private String name;

    @Column(name = "department_description")
    private String description;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @ManyToMany
    @JsonIgnoreProperties("departments")
    @JoinTable(name = "department_partner_mp", joinColumns = @JoinColumn(name = "department_id")
            , inverseJoinColumns = @JoinColumn(name = "partner_id"))
    private Set<Partner> partners;

    @JsonIgnore
    @OneToMany(mappedBy = "departmentEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedDepartment> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

}

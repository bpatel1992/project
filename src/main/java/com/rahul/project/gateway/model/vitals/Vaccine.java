package com.rahul.project.gateway.model.vitals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.model.LocalizedVaccine;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User Role Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "Vaccine_m")
public class Vaccine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "vaccine_name")
    private String vaccineName;

    @NotNull
    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @NotNull
    @Column(name = "cost")
    private BigDecimal cost;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @Basic
    @CreatedDate
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @NotNull
    @Column(name = "created_by")
    private String createdBy;

    @Basic
    @LastModifiedDate
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modificationDate;

    @NotNull
    @Column(name = "modified_by")
    private String modifiedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "vaccine", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedVaccine> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }

}

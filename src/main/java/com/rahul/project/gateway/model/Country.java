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
 * date 2019-05-21
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "country_m")
public class Country implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "status")
    private boolean status;
    @Basic
    @Column(name = "code")
    private Integer code;
    @Basic
    @Column(name = "from_length")
    private Integer fromLength;
    @Basic
    @Column(name = "to_length")
    private Integer toLength;
    @Basic
    @Column(name = "description")
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "countryEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedCountry> localizations = new HashMap<>();
    @JsonInclude()
    @Transient
    private String label;

    public Country(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }
}

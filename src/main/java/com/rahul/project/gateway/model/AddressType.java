package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "address_type_m")
public class AddressType implements Serializable {

    @Id
    @SequenceGenerator(name = "AddressType_gen", allocationSize = 1, sequenceName = "AddressType_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressType_gen")
    private Long id;

    @Column(name = "address_type")
    private String addressType;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "addressTypeEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedAddressType> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }
}

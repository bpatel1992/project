package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "wtdn_m")
public class WTDN implements Serializable {

    @Id
    @SequenceGenerator(name = "wtdn_gen", allocationSize = 1, sequenceName = "wtdn_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wtdn_gen")
    private Long id;

    @Column(name = "WTDN")
    @Type(type = "text")
    private String wtdn;

    @JsonIgnore
    @OneToMany(mappedBy = "wtdnEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedWTDN> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getWtdn() : "";
    }
}

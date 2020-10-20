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
@Table(name = "gender_m")
public class Gender implements Serializable {
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
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;
    @JsonIgnore
    @OneToMany(mappedBy = "genderEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedGender> localizations = new HashMap<>();
    @JsonInclude()
    @Transient
    private String label;

    public Gender(Long id) {
        this.id = id;
    }

    public String getLabel() {
        if (localizations != null && localizations.size() > 0)
            return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                    ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
        else
            return null;
    }
}

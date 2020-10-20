package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Table(name = "title_m")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Title implements Serializable {
    @Id
    @SequenceGenerator(name = "title_gen", allocationSize = 1, sequenceName = "title_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "title_gen")
    private Long id;
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;
    @JsonIgnore
    @OneToMany(mappedBy = "titleEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedTitle> localizations = new HashMap<>();
    @JsonInclude()
    @Transient
    private String label;

    public Title(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }
}

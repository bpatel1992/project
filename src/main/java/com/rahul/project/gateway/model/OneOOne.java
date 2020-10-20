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
@Table(name = "one_o_one_m")
public class OneOOne implements Serializable {

    @Id
    @SequenceGenerator(name = "one_o_one_gen", allocationSize = 1, sequenceName = "one_o_one_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "one_o_one_gen")
    private Long id;

    @Column(name = "title")
    private String title;

    @JsonInclude()
    @Transient
    private String content;

    @ManyToOne
    @JoinColumn(name = "content_type")
    private ContentType contentType;

    @JsonIgnore
    @OneToMany(mappedBy = "oneOOneEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedOneOOne> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    @JsonInclude()
    @Transient
    private String fileType;

    @JsonInclude()
    @Transient
    private String fileName;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

    public String getFileName() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getFileName() : "";
    }

    public String getFileType() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getFileType() : "";
    }

    public String getContent() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getContent() : "";
    }
}

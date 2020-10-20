package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "localized_language")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedLanguage implements Serializable {

    @EmbeddedId
    private LocalizedStringId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label"})
    private Language language;

    @Column(name = "label")
    private String label;


}

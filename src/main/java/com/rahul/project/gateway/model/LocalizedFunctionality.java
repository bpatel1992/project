package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "localized_functionality_m")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedFunctionality implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label"})
    private Functionality functionalityEntity;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

}

package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "localized_symptom")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedSymptom implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
//    @JsonIgnore
    @JsonIgnoreProperties({"localizations", "label", "symptomName", "symptomNode"})
    private Symptom symptomEntity;

    @Column(name = "label")
    private String label;

}

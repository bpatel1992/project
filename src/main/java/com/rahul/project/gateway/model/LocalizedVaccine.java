package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rahul.project.gateway.model.vitals.Vaccine;
import com.rahul.project.gateway.model.vitals.VitalService;
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
@Table(name = "localized_vaccine_m")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedVaccine implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label"})
    private Vaccine vaccine;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

}

package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "localized_wtdn")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedWTDN implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "wtdn", "label", "tip"})
    private WTDN wtdnEntity;

    @Column(name = "WTDN")
    @Type(type = "text")
    private String wtdn;

}

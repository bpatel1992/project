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
@Table(name = "localized_status_type_m")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedStatusType implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label"})
    private StatusType statusTypeEntity;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

}

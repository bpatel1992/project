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
@Table(name = "localized_possible_cause_priority")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedPossibleCausePriority implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label", "tip", "priority", "description", "colour"})
    private PossibleCausePriority possibleCausePriorityEntity;

    @Column(name = "priority")
    private String priority;

    @Column(name = "description")
    @Type(type = "text")
    private String description;
}

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
@Table(name = "localized_one_o_one")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedOneOOne implements Serializable {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne()
    @MapsId("id")
    @JoinColumn(name = "id")
    @JsonIgnoreProperties({"localizations", "label"})
    private OneOOne oneOOneEntity;

    @Column(name = "label")
    @Type(type = "text")
    private String label;

    @Column(name = "fileType")
    private String fileType;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "content")
    @Type(type = "text")
    private String content;

}

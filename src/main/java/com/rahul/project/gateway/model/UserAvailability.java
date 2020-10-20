package com.rahul.project.gateway.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_availability")
public class UserAvailability extends BaseEntity {

    @Id
    @SequenceGenerator(name = "user_availability_gen", allocationSize = 1, sequenceName = "user_availability_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_availability_gen")
    private long id;

    @ManyToOne
    @JoinColumn(name = "attendant_id")
    @JsonIgnoreProperties({"userPetRelationMPS", "authorities"})
    private User attendant;

    @ManyToOne
    @JoinColumn(name = "status_type_id")
    private StatusType statusType;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private PartnerAddress clinic;

    @JoinColumn(name = "from_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;

    @JoinColumn(name = "to_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toTime;

    @Basic
    @CreatedDate
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @Basic
    @LastModifiedDate
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modificationDate;

}

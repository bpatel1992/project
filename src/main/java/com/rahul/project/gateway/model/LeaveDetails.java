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
@Table(name = "leave_details_m")
public class LeaveDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @JsonIgnoreProperties({"partnerAddresses", "partnerTypes", "users"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_type_id")
    private StatusType statusType;

    @Basic
    @Column(name = "from_date_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fromDateTime;

    @Basic
    @Column(name = "to_date_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date toDateTime;

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

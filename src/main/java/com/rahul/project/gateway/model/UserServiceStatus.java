package com.rahul.project.gateway.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rahul Malhotra
 * @Date : 25-Feb-2019 at 2:33:57 pm
 */
@Data
@Entity
@Table(name = "user_service_status_m")
public class UserServiceStatus implements Serializable {

    @Id
    @SequenceGenerator(name = "user_service_status_m_gen", allocationSize = 1, sequenceName = "user_service_status_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_service_status_m_gen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services serviceId;

    @Basic
    @Column(name = "validity_from_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validityFromDate;

    @Basic
    @Column(name = "validity_to_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date validityToDate;

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

    @Column(name = "status", length = 3)
    private String status;
}

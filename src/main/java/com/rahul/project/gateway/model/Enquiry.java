package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enquiry")
@EntityListeners(AuditingEntityListener.class)
public class Enquiry implements Serializable {
    @Id
    @SequenceGenerator(name = "enquiry_gen", allocationSize = 1, sequenceName = "enquiry_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enquiry_gen")
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "title_id", referencedColumnName = "id")
    private Title title;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Email
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "mobile_country_id", referencedColumnName = "id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "partner_user_id")
    private User attendant;
    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private User customerId;
    @Basic
    @Column(name = "mobile")
    private String mobile;
    @Basic
    @Column(name = "message")
    @Type(type = "text")
    private String message;
    @Basic
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;
    @Basic
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modificationDate;
    @Basic
    @Column(name = "time_zone")
    private String timeZone;
    public Enquiry(Long id) {
        this.id = id;
    }
}

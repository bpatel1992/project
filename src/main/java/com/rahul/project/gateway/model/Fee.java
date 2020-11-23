package com.rahul.project.gateway.model;


import com.rahul.project.gateway.enums.FeeStatus;
import com.rahul.project.gateway.enums.TaxType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "fee_m")
public class Fee extends BaseEntity{

    @Id
    @SequenceGenerator(name = "fee_m_gen", allocationSize = 1, sequenceName = "fee_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_m_gen")
    private long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;

    @ManyToOne
    @JoinColumn(name = "customer_type_id")
    private Authority authority;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "tax_type")
    private TaxType taxType;

    @Column(name = "fee_type")
    private FeeStatus feeType;

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

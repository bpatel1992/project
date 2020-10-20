package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "partner_services")
public class PartnerServices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private Partner partnerId;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services serviceId;

    @Column
    private BigDecimal discount;

    @Column
    private BigDecimal offeredPrice;

    @Column
    private BigDecimal finalPrice;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;
}

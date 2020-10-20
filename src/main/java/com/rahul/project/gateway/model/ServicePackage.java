package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "service_package_m")
public class ServicePackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "package_name")
    private String packageName;

    @Basic
    @Column(name = "package_mrp")
    private BigDecimal packageMRP;

    @Basic
    @Column(name = "offered_price")
    private BigDecimal offeredPrice;

    @Basic
    @Column(name = "discount")
    private BigDecimal discount;


    @ManyToMany()
    @JoinTable(name = "package_services_mp", joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Services> services;
}

package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "service_package_m")
public class ServicePackage implements Serializable {

    @Id
    @SequenceGenerator(name = "service_package_m_gen", allocationSize = 1, sequenceName = "service_package_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_package_m_gen")
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "package_name")
    private String packageName;

    @Basic
    @Column(name = "display_order")
    private Integer displayOrder;

    @Basic
    @Column(name = "validity_in_months")
    private Integer validityInMonths;

    @Basic
    @Column(name = "package_mrp")
    private BigDecimal packageMRP;

    /*@Basic
    @Column(name = "offered_price")
    private BigDecimal offeredPrice;*/

    @Basic
    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;


    /*@ManyToMany()
    @JoinTable(name = "package_services_mp", joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Services> services;*/
}

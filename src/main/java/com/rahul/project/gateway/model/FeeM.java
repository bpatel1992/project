package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "fee_master_m")
public class FeeM implements Serializable {

    private static final long serialVersionUID = -7978731893542660269L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "fee_name")
    private String feeName;
    @Column(name = "description")
    private String description;
    @Column(name = "fee_type")
    private String feeType;
    @Column(name = "fee_value", precision = 10, scale = 2)
    private BigDecimal feeValue;
    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    public FeeM() {
    }

    public FeeM(Long id) {
        this.id = id;
    }

}

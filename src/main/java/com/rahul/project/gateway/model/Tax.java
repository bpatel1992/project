package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "txn_transfer_tax_m")
public class Tax implements Serializable {

    @Id
    @SequenceGenerator(name = "txn_transfer_tax_gen", sequenceName = "txn_transfer_tax_seq")
    @GeneratedValue(generator = "txn_transfer_tax_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "description")
    private String description;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "tax_value", precision = 10, scale = 2)
    private BigDecimal taxValue;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;
}

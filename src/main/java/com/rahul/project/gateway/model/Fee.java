package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "txn_transfer_fee_m")
public class Fee implements Serializable {

    @Id
    @SequenceGenerator(name = "txn_transfer_fee_gen", sequenceName = "txn_transfer_fee_seq")
    @GeneratedValue(generator = "txn_transfer_fee_gen", strategy = GenerationType.SEQUENCE)
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

    @Column(name = "fee_amount", precision = 10, scale = 2)
    private BigDecimal feeAmount;

}

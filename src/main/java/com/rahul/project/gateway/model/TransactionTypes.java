package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@SequenceGenerator(name = "TransactionTypes_SEQ", allocationSize = 1, sequenceName = "TransactionTypes_SEQ")
@Table(name = "TRANSACTION_TYPES")
public class TransactionTypes implements Serializable {

    private static final long serialVersionUID = 6734081956196446356L;

    @Id
    @GeneratedValue(generator = "TransactionTypes_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "TRANSACTION_CODE")
    private String transactionCode;

    @Column(name = "TRANSACTION_NAME")
    private String transactionName;
}

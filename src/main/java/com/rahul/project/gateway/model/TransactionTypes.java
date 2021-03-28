package com.rahul.project.gateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "transaction_types_seq", allocationSize = 1, sequenceName = "transaction_types_seq")
@Table(name = "transaction_types")
public class TransactionTypes implements Serializable {

    private final long serialVersionUID = 6734081956196446356L;

    public TransactionTypes(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "transaction_types_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "transaction_name")
    private String transactionName;
}

package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "txn_transfer_m")
public class Transfer implements Serializable {

    @Id
    @SequenceGenerator(name = "txn_transfer_gen", sequenceName = "txn_transfer_seq")
    @GeneratedValue(generator = "txn_transfer_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "txn_id")
    private Transaction transaction;

    @ManyToOne
    @JsonIgnoreProperties({"categoryListM", "departments"})
    @JoinColumn(name = "PARTNER_CODE")
    private Partner partner;

    @ManyToMany
    @JoinTable(name = "txn_transfer_fee_mp", joinColumns = @JoinColumn(name = "transfer_id")
            , inverseJoinColumns = @JoinColumn(name = "fee_id"))
    private Set<Fee> fees;

    @ManyToMany
    @JoinTable(name = "txn_transfer_tax_mp", joinColumns = @JoinColumn(name = "transfer_id")
            , inverseJoinColumns = @JoinColumn(name = "tax_id"))
    private Set<Tax> taxes;

    @Column(name = "fee", precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(name = "tax", precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private Currency currency;

    @Column(name = "REQUEST_TYPE")
    private String requestType;

}

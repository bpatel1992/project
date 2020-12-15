package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TAX_M")
@Getter
@Setter
public class TaxM implements Serializable {

    private static final long serialVersionUID = 6959089711126534521L;
    @Id
    @SequenceGenerator(name = "TAXM_GEN", allocationSize = 1, sequenceName = "TAXM_SEQ")
    @GeneratedValue(generator = "TAXM_GEN", strategy = GenerationType.SEQUENCE)
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
    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    public TaxM() {
    }

    public TaxM(Long id) {
        this.id = id;
    }
}

package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "external_factor_m")
public class ExternalFactors implements Serializable {

    @Id
    @SequenceGenerator(name = "external_factor_gen", allocationSize = 1, sequenceName = "external_factor_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "external_factor_gen")
    private Long id;

    @Column(name = "external_factor_name", length = 100)
    private String externalFactor;

}

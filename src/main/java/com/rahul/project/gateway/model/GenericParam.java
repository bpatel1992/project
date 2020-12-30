package com.rahul.project.gateway.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "generic_params_m")
public class GenericParam implements Serializable {

    private static final long serialVersionUID = 6086641479590121132L;

    @Id
    @SequenceGenerator(name = "generic_params_m_gen", allocationSize = 1, sequenceName = "generic_params_m_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generic_params_m_gen")
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", length = 50)
    private String name;

    @Basic
    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private boolean active;

}

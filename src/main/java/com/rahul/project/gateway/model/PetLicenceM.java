package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "pet_licence_m")
public class PetLicenceM implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pf_id", referencedColumnName = "id")
    private PetType petType;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;
}

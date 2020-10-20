package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-22
 */
@Data
@Entity
@Table(name = "pet_physical_attributes")
public class PetPhysicalAttributes implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "data")
    private String data;
}

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
@Table(name = "pet_breed_lifespan")
public class PetBreedLifespan implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "lower_limit")
    private Double lowerLimit;

    @Basic
    @Column(name = "upper_limit")
    private Double upperLimit;
}

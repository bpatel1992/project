package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * It is the pet breed group : toy, small, medium, large and giant
 *
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "pet_breed_group_m")
public class PetBreedGroup implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;
}

package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-25
 */
@Data
@Entity
@Table(name = "pet_puppy_age_range")
public class PetPuppyAgeRange implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "minimum")
    private Long minimum;

    @Basic
    @Column(name = "maximum")
    private Long maximum;
}

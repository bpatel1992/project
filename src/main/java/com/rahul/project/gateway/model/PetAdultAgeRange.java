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
@Table(name = "pet_adult_age_range")
public class PetAdultAgeRange implements Serializable {

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
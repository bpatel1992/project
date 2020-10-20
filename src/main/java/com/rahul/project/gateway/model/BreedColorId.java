package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Embeddable
public class BreedColorId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private PetBreed petBreed;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private PetColor petColor;
}

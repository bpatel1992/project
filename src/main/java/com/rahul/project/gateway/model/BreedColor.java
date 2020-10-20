package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Entity
@Data
@Table(name = "breed_color")
public class BreedColor implements Serializable {

    @EmbeddedId
    private BreedColorId breedColorId;
}

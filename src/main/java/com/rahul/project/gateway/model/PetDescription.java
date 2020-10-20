package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-26
 */
@Data
@Entity
@Table(name = "pet_desc")
public class PetDescription implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;
}

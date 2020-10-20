package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * It is the category of the content : health, care, food, nutrition, behavior, training, emergency and pregnancy.
 *
 * @author rahul malhotra
 * date 2019-08-29
 */
@Data
@Entity
@Table(name = "category_m")
public class Category implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;
}

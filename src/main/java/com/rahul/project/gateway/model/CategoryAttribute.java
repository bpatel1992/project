package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * It is the category attribute behaviour: socialization, growling ;care:teething, sleep patterns
 *
 * @author rahul malhotra
 * date 2019-08-29
 */
@Data
@Entity
@Table(name = "category_attribute_m")
public class CategoryAttribute implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ct_id", referencedColumnName = "id")
    private Category category;
}

package com.rahul.project.gateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * @Date 2019-04-21
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "weight_unit_m")
public class WeightUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;
    @Basic
    @Column(name = "w_desc")
    private String description;

    public WeightUnit(Long id) {
        this.id = id;
    }
}

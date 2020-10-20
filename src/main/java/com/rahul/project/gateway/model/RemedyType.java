package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "remedy_type_m")
public class RemedyType implements Serializable {

    @Id
    @SequenceGenerator(name = "remedy_type_gen", allocationSize = 1, sequenceName = "remedy_type_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remedy_type_gen")
    private Long id;

    @Column(name = "remedy", length = 100)
    private String remedy;

}

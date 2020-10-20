package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "remedy_m")
public class Remedy implements Serializable {

    @ManyToOne
    @JoinColumn(name = "remedy_type")
    RemedyType remedyType;
    @Id
    @SequenceGenerator(name = "home_remedy_gen", allocationSize = 1, sequenceName = "home_remedy_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_remedy_gen")
    private Long id;
    @Column(name = "remedy", length = 100)
    private String remedy;
}

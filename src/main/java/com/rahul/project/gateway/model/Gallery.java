package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gallery_m")
public class Gallery implements Serializable {

    @Id
    @SequenceGenerator(name = "gallery_gen", allocationSize = 1, sequenceName = "gallery_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gallery_gen")
    private Long id;
    @Column(name = "name")
    private String name;

    public Gallery(String name) {
        this.name = name;
    }

}

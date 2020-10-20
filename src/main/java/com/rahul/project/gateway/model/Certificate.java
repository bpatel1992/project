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
@Table(name = "certificate_m")
public class Certificate implements Serializable {

    @Id
    @SequenceGenerator(name = "certificate_gen", allocationSize = 1, sequenceName = "certificate_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificate_gen")
    private Long id;
    @Column(name = "name")
    private String name;

    public Certificate(String name) {
        this.name = name;
    }

}

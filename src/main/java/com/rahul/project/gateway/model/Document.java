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
@Table(name = "document_m")
public class Document implements Serializable {

    @Id
    @SequenceGenerator(name = "document_gen", allocationSize = 1, sequenceName = "document_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_gen")
    private Long id;
    @Column(name = "name")
    private String name;

    public Document(String name) {
        this.name = name;
    }

}

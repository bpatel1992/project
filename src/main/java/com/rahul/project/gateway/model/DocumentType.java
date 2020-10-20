package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @SequenceGenerator(name = "document_type_gen", allocationSize = 1, sequenceName = "document_type_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_type_gen")
    @Column(name = "id")
    private long id;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "page_quantity")
    private Integer pageQuantity = 1;

}

package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "partner_document")
public class PartnerDocument implements Serializable {

    @Id
    @SequenceGenerator(name = "partner_document_gen", allocationSize = 1, sequenceName = "partner_document_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_document_gen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    @JsonIgnoreProperties({"partnerAddresses", "partnerTypes", "users"})
    @JsonIgnore
    private Partner partner;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentType document;

    @Column(name = "document_number", length = 20)
    private String documentNumber;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileURL;

    @Column(name = "title")
    private String title;

    @Column(name = "display_order")
    private Integer displayOrder;
}

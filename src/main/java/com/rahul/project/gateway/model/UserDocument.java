package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_document")
@EntityListeners(AuditingEntityListener.class)
public class UserDocument implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private DocumentType documentType;

    @Id
    @SequenceGenerator(name = "user_document_gen", allocationSize = 1, sequenceName = "user_document_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_document_gen")
    @Column(name = "id")
    private Long id;
    @Column(name = "document_number", length = 20)
    private String documentNumber;
    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "video_code")
    private String videoCode;

    @Column(name = "display_order")
    private Integer displayOrder;
    @Basic
    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationDate;
    @Basic
    @Column(name = "modification_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modificationDate;

    public UserDocument(User user, DocumentType documentType, String title, Integer displayOrder) {
        this.user = user;
        this.documentType = documentType;
        this.title = title;
        this.displayOrder = displayOrder;
    }

    public UserDocument(Long id, User user, DocumentType documentType, String title, Integer displayOrder) {
        this.id = id;
        this.user = user;
        this.documentType = documentType;
        this.title = title;
        this.displayOrder = displayOrder;
    }
}

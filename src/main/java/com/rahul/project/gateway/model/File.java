package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "file_m")
public class File implements Serializable {

    @Id
    @Basic
    private Long id;

    @Basic
    @Column(name = "document_name")
    private String documentName;

    @Basic
    @Column
    private boolean status;
}

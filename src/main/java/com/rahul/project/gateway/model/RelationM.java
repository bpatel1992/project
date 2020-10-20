package com.rahul.project.gateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "relation_m")
public class RelationM implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "relation_name")
    private String relationName;
    @Basic
    @Column(name = "status")
    private boolean status;
    @Basic
    @Column(name = "relation_desc")
    private String relationDesc;

    public RelationM(Long id) {
        this.id = id;
    }
}

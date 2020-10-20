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
@Table(name = "partner_type_m")
public class PartnerTypeM implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "partner_type_desc")
    private String partnerTypeDesc;

    @Basic
    @Column(name = "status")
    private boolean status;
}

package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author rahul malhotra
 * date 2020-09-22
 */
@Getter
@Setter
@Entity
@Table(name = "user_partner_relation_mp")
public class UserPartnerRelationMP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mapping_id")
    private Long id;

    @ManyToOne()
    //@MapsId("id1")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    //@MapsId("id2")
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne()
    //@MapsId("id3")
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private RelationM relation;

    @Column(name = "created", nullable = false)
    private Date created = new Date();

}

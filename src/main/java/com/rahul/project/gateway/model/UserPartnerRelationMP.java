package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2020-09-22
 */
@Getter
@Setter
@Entity
@Table(name = "user_partner_relation_mp")
public class UserPartnerRelationMP implements Serializable {

    @EmbeddedId
    private CompositeId id;

    @ManyToOne()
    @MapsId("id1")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @MapsId("id2")
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private RelationM relation;

}

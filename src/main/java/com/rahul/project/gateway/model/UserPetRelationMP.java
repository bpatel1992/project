package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@Entity
@Table(name = "user_pet_relation_mp")
public class UserPetRelationMP implements Serializable {

    @EmbeddedId
    private UserPetRelationMpPK id;

    @ManyToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private RelationM relationM;

}

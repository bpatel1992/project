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
@Table(name = "user_relation_mp")
@IdClass(UserRelationMpPK.class)
public class UserRelationMp implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    private User fromUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private RelationM relationM;
}

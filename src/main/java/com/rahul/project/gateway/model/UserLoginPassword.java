package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 */

@Data
@Entity
//@IdClass(value = UserLoginPasswordIdClass.class)
@Table(name = "user_login_password")
public class UserLoginPassword implements Serializable {

    @EmbeddedId
    private CompositeId compositeId;

    @ManyToOne()
    @MapsId("id1")
    @JoinColumn(name = "login_type_id")
    private UserLoginType loginType;

    @ManyToOne()
    @MapsId("id2")
    @JoinColumn(name = "user_id")
    private User user;

    @Basic
    @Column(name = "password")
    private String password;
}

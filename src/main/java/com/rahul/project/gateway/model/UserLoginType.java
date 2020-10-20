package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author rahul malhotra
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_login_type")
public class UserLoginType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_type")
    private String loginType;
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;

    public UserLoginType(Long id) {
        this.id = id;
    }
}

package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author rahul malhotra
 */
@Data
public class UserLoginPasswordIdClass implements Serializable {

    @ManyToOne
    @JoinColumn(name = "login_type_id")
    private UserLoginType loginType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

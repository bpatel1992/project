package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "user_authority")
public class UserAuthority implements Serializable {

    @EmbeddedId
    private UserAuthorityId userAuthorityId;
}

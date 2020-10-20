package com.rahul.project.gateway.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This Entity denotes the product user in the application
 *
 * @author Rahul Malhotra
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "PRODUCTUSER_SEQ", initialValue = 50, sequenceName = "PRODUCTUSER_SEQ")
@Table(name = "ProductUser", indexes = {@Index(name = "ProductUser_Idx", columnList = "emailAddress", unique = true)})
public class ProductUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTUSER_SEQ")
    private Long primaryKey;

    private String firstName;
    private String lastName;
    private boolean isActive;
    private boolean isLocked;
    private String emailAddress;
    private String password;

    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp lastUpdatedAt;

    private Timestamp lastLoggedInAt;

    @ManyToOne
    @JoinColumn(name = "privilegeId")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

/*    public boolean canTakeAction(Privilege otherPrivilege) {
        boolean canTakeAction = false;
        if (this.privilege != null && otherPrivilege != null) {
            canTakeAction = this.privilege.havingHigherOrEqualAccess(otherPrivilege);
        }
        return canTakeAction;
    }*/
}

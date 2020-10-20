package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This entity maps Role to Menu
 *
 * @author Rahul Malhotra
 */
@Data
@Entity
@SequenceGenerator(name = "ROLEMENUMP_SEQ", initialValue = 50, sequenceName = "ROLEMENUMP_SEQ")
@Table(name = "RoleMenuMp", indexes = {@Index(name = "RoleMenuMp_Idx", columnList = "ROLEID, MENUID", unique = true)})
public class RoleMenuMp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLEMENUMP_SEQ")
    private Long primaryKey;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "menuId", nullable = false)
    private Menu menu;
}
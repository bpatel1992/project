package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Menu of the application. ParentMenu : Denotes if this menu has any parent
 * menu
 *
 * @author Rahul Malhotra
 */
@Data
@Entity
@SequenceGenerator(name = "MENU_SEQ", initialValue = 50, sequenceName = "MENU_SEQ")
@Table(name = "Menu", indexes = {@Index(name = "Menu_Idx", columnList = "menuID", unique = true)})
public class Menu implements Serializable {

    public boolean isActive;
    @Id
    @SequenceGenerator(name = "MENU_GEN", initialValue = 50, sequenceName = "MENU_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_GEN")
    private Long primaryKey;
    private String menuId;
    private String menuName;
    private String routingUrl;
    private String menuIcon;

    /**
     * Parent Menu of this Menu
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parentMenuId")
    private Menu parentMenu;

    /**
     * A Menu can have other Sub Menus, So maintaining a chain of menus here
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "parentMenu")
    private Set<Menu> subMenuList = new LinkedHashSet<>();
}

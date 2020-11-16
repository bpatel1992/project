package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * role Functionality Entity
 *
 * @author Rahul Malhotra
 */
@Getter
@Setter
@Entity
@Table(name = "role_functionality_m")
public class RoleFunctionality implements Serializable {

    private static final long serialVersionUID = -9064172068782084192L;

    @Id
    @SequenceGenerator(name = "role_functionality_gen", allocationSize = 1, sequenceName = "role_functionality_seq")
    @GeneratedValue(generator = "role_functionality_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"features", "roles", "localizations"})
    @JoinColumn(name = "func_id")
    private Functionality functionality;

    @ManyToMany
    @JsonIgnoreProperties({"roles", "localizations"})
    @JoinTable(name = "role_functionality_m_privilege_mp", joinColumns = @JoinColumn(name = "role_functionality_id")
            , inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges;

    @ManyToMany
    @JsonIgnoreProperties({"functionaries", "localizations"})
    @JoinTable(name = "role_functionality_feature_mp", joinColumns = @JoinColumn(name = "role_functionality_id")
            , inverseJoinColumns = @JoinColumn(name = "role_feature_id"))
    private Set<RoleFeature> features;

}

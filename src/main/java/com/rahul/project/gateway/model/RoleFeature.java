package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * role Role Entity
 *
 * @author Rahul Malhotra
 */
@Setter
@Getter
@Entity
@Table(name = "role_feature_m")
public class RoleFeature implements Serializable {

    private static final long serialVersionUID = 7946048187145260206L;

    @Id
    @SequenceGenerator(name = "role_feature_gen", allocationSize = 1, sequenceName = "role_feature_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_feature_gen")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"functionaries", "localizations"})
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToMany
    @JsonIgnoreProperties({"roles", "localizations"})
    @JoinTable(name = "role_functionality_feature_privilege_mp", joinColumns = @JoinColumn(name = "role_functionality_feature_id")
            , inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges;

}

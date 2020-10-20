package com.rahul.project.gateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "services_m")
public class Services implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "service_name")
    private String serviceName;
    @Basic
    @Column(name = "service_desc")
    private String serviceDesc;
    @Basic
    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "parent_service_id", referencedColumnName = "id")
    private Services parentServiceId;
    @ManyToOne
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    private ServiceType serviceTypeId;
    @ManyToMany()
    @JoinTable(name = "service_features_mp", joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<ServiceFeatures> serviceFeatures;


    public Services(long id) {
        this.id = id;
    }
}


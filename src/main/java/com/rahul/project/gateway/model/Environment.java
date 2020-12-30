package com.rahul.project.gateway.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "environment_m")
public class Environment implements Serializable {

    private static final long serialVersionUID = 1203489962089081214L;

    @Id
    @Column(name = "environment", length = 50)
    private String environment;

    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private boolean active;

}

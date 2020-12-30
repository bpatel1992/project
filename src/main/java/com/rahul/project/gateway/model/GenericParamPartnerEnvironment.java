package com.rahul.project.gateway.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "generic_params_partner_env_m")
public class GenericParamPartnerEnvironment implements Serializable {

    private static final long serialVersionUID = 6183837086246724557L;

    @EmbeddedId
    private GenericParamPartnerEnvironmentIdClass paramConfig;

    @Column(name = "param_value")
    private String value;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;
}

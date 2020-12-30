package com.rahul.project.gateway.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class GenericParamPartnerEnvironmentIdClass implements Serializable {

    private static final long serialVersionUID = -1603094217422274020L;

    @ManyToOne
    @JoinColumn(name = "partner_code")
    @JsonIgnoreProperties({"departments", "addressM", "walletAccountM", "tenants", "categoryListM"})
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "param_code")
    private GenericParam param;

    @ManyToOne
    @JoinColumn(name = "environment")
    private Environment environment;
}

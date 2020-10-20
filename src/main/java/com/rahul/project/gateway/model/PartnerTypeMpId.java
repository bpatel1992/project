package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class PartnerTypeMpId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private Partner partnerId;

    @ManyToOne
    @JoinColumn(name = "partner_type_id", referencedColumnName = "id")
    private PartnerTypeM partnerTypeM;
}

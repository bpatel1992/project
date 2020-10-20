package com.rahul.project.gateway.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "partner_type_mp")
public class PartnerTypeMp implements Serializable {

    @EmbeddedId
    private PartnerTypeMpId partnerTypeMpId;
}

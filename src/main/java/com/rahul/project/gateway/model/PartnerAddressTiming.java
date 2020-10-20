package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-25
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "partner_address_partner4")
public class PartnerAddressTiming implements Serializable {

    @EmbeddedId
    private CompositeId id;
    @ManyToOne
    @MapsId("id1")
    @JoinColumn(name = "address_id")
    private PartnerAddress partnerAddress;
    @ManyToOne()
    @MapsId("id2")
    @JsonIgnoreProperties({"users", "partnerAddresses"})
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToMany
    @JoinTable(name = "partner_address_business_timing_mp4",
            joinColumns = {@JoinColumn(name = "partner_id", referencedColumnName = "partner_id"),
                    @JoinColumn(name = "address_id", referencedColumnName = "address_id")},
            inverseJoinColumns = @JoinColumn(name = "business_timing_id"))
    private Set<BusinessTiming> businessTimings;

    public PartnerAddressTiming(Partner partner, PartnerAddress partnerAddress, Set<BusinessTiming> businessTimings) {
        this.id = new CompositeId(partnerAddress.getId(), partner.getId());
        this.partnerAddress = partnerAddress;
        this.partner = partner;
        if (businessTimings != null)
            this.businessTimings = businessTimings;

    }
}

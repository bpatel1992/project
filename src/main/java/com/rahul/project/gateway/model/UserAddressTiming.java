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
@Table(name = "partner_address_user1")
public class UserAddressTiming implements Serializable {

    @ManyToMany
    @JoinTable(name = "user_address_business_timing_mp2",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
                    @JoinColumn(name = "address_id", referencedColumnName = "address_id")},
            inverseJoinColumns = @JoinColumn(name = "business_timing_id"))
    private Set<BusinessTiming> businessTimings;

    @EmbeddedId
    private CompositeId id;

    @ManyToOne
    @MapsId("id1")
    @JoinColumn(name = "address_id")
    private PartnerAddress partnerAddress;

    @ManyToOne
    @MapsId("id2")
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("partners")
    private User user;

    public UserAddressTiming(User user, PartnerAddress partnerAddress, Set<BusinessTiming> businessTimings) {
        this.id = new CompositeId(partnerAddress.getId(), user.getId());
        this.partnerAddress = partnerAddress;
        this.user = user;
        if (businessTimings != null)
            this.businessTimings = businessTimings;

    }
}

package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.BusinessTiming;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.PartnerAddressTiming;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * PartnerAddressTiming Repository to handle any PartnerAddressTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PartnerAddressTimingRepository")
public interface PartnerAddressTimingRepository extends BaseRepository<PartnerAddressTiming, Long> {
    PartnerAddressTiming getByPartnerAndPartnerAddress(Partner partner, PartnerAddress partnerAddress);

    @Query(value = "SELECT bt FROM PartnerAddressTiming pa join pa.businessTimings bt " +
            " WHERE pa.partner.id = ?1 and pa.partnerAddress.id = ?2 ")
    Set<BusinessTiming> businessTimings(Long partnerId, Long partnerAddressId);
}

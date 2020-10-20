package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.PartnerAddressTiming;
import org.springframework.stereotype.Repository;

/**
 * PartnerAddressTiming Repository to handle any PartnerAddressTiming related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PartnerAddressTimingRepository")
public interface PartnerAddressTimingRepository extends BaseRepository<PartnerAddressTiming, Long> {
    PartnerAddressTiming getByPartnerAndPartnerAddress(Partner partner, PartnerAddress partnerAddress);
}

package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PartnerAddress;
import org.springframework.stereotype.Repository;

/**
 * PartnerAddress Repository to handle any PartnerAddress related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PartnerAddressRepository")
public interface PartnerAddressRepository extends BaseRepository<PartnerAddress, Long> {
}

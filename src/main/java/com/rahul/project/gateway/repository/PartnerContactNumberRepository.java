package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PartnerContactNumber;
import org.springframework.stereotype.Repository;

/**
 * PartnerContactNumber Repository to handle any PartnerContactNumber related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PartnerContactNumberRepository")
public interface PartnerContactNumberRepository extends BaseRepository<PartnerContactNumber, Long> {
}

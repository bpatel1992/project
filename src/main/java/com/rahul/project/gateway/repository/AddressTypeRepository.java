package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.AddressType;
import org.springframework.stereotype.Repository;

/**
 * AddressType Repository to handle any AddressType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "AddressTypeRepository")
public interface AddressTypeRepository extends BaseRepository<AddressType, Long> {
}

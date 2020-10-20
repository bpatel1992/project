package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ServiceType;
import org.springframework.stereotype.Repository;

/**
 * ServiceType Repository to handle any ServiceType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "ServiceTypeRepository")
public interface ServiceTypeRepository extends BaseRepository<ServiceType, Long> {
}

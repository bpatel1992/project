package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.RoleFunctionality;
import org.springframework.stereotype.Repository;

/**
 * RoleFunctionality Repository to handle any RoleFunctionality related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "RoleFunctionalityRepository")
public interface RoleFunctionalityRepository extends BaseRepository<RoleFunctionality, Long> {
}

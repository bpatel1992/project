package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PetType;
import org.springframework.stereotype.Repository;

/**
 * PetType Repository to handle any PetType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PetTypeRepository")
public interface PetTypeRepository extends BaseRepository<PetType, Long> {
}

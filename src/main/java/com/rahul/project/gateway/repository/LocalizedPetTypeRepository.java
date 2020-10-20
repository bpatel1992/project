package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.LocalizedId;
import com.rahul.project.gateway.model.LocalizedPetType;
import org.springframework.stereotype.Repository;

/**
 * LocalizedPetType Repository to handle any LocalizedPetType related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "LocalizedPetTypeRepository")
public interface LocalizedPetTypeRepository extends BaseRepository<LocalizedPetType, LocalizedId> {
}

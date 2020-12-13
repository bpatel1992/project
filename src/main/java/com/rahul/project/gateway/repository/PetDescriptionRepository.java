package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PetDescription;
import org.springframework.stereotype.Repository;

/**
 * PetDescriptionRepository to handle any PetDescription related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PetDescriptionRepository")
public interface PetDescriptionRepository extends BaseRepository<PetDescription, Long> {
}

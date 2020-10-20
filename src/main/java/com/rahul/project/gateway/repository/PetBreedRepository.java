package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PetBreed;
import org.springframework.stereotype.Repository;

/**
 * PetBreedRepository to handle any PetBreed related Operations
 *
 * @author Rahul Malhotra
 */
@Repository
public interface PetBreedRepository extends BaseRepository<PetBreed, Long> {
}

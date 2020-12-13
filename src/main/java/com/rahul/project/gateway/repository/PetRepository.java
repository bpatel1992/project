package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.Pet;
import org.springframework.stereotype.Repository;

/**
 * PetRepository to handle any Pet related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PetRepository")
public interface PetRepository extends BaseRepository<Pet, Long> {
    Pet getByRandomKey(String s);
}

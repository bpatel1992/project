package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PetSign;
import org.springframework.stereotype.Repository;

/**
 * PetSignRepository to handle any PetSign related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PetSignRepository")
public interface PetSignRepository extends BaseRepository<PetSign, Long> {
}

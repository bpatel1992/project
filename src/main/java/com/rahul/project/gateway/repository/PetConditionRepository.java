package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.PetCondition;
import org.springframework.stereotype.Repository;

/**
 * PetConditionRepository to handle any PetCondition related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "PetConditionRepository")
public interface PetConditionRepository extends BaseRepository<PetCondition, Long> {
}
